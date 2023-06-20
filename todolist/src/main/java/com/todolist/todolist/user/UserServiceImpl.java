package com.todolist.todolist.user;

import java.sql.Date;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.todolist.todolist.email.EmailService;
import com.todolist.todolist.exception.DuplicateEmailException;
import com.todolist.todolist.exception.EntityNotFoundException;
import com.todolist.todolist.security.SecurityConstants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    EmailService emailService;

    @Override
    public User saveUser(User user) {
        user.setVerification(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        String confirmtoken = generateConfirmToken(user.getEmail());
        user.setConfirmtoken(confirmtoken);

        String link = "http://localhost:8080/user/register/confirmtoken=" + confirmtoken;
        emailService.sendEmail(user.getEmail(), "Confirm account", emailService.buildEmail(user.getEmail(), link));

        Boolean check = userRepository.existsByEmail(user.getEmail());
        if(check == false) {
            return userRepository.save(user);
        }
        else {
            throw new DuplicateEmailException(user.getEmail());
        }
    }

    @Override
    public User getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return unwrapUser(user, 404L);
    }

    @Override
    public User getUserByConfirmToken(String comfirmtoken) {
        Optional<User> user = userRepository.findByConfirmtoken(comfirmtoken);
        return unwrapUser(user, 404L);
    }

    @Override
    public String confirmToken(String confirmtoken) {
        User user = getUserByConfirmToken(confirmtoken);
        System.out.println(user.getConfirmtoken());
        System.out.println(confirmtoken);
        if(user.getConfirmtoken().equals(confirmtoken)) {
            user.setVerification(true);
            userRepository.save(user);
            return "Account is verified";
        }
        else {
            return "JWT is not valid!";
        }
    }

    @Override
    public String generateConfirmToken(String email) {
        String token = JWT.create()
        .withSubject(email)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.CONFIRMTOKEN_EXPIRATION))
        .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));

        return token;
    }

    public static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
}
