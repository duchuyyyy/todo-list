package com.todolist.todolist.user;

import java.sql.Date;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
    public void saveUser(User user) {
        Boolean check = userRepository.existsByEmail(user.getEmail());
        if(check == false) {
            user.setVerification(false);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            
            String confirmtoken = generateConfirmToken(user.getEmail());
            user.setConfirmtoken(confirmtoken);
    
            String link = "http://localhost:3000/verification-account/" + confirmtoken;
            emailService.sendEmail(user.getEmail(), "Confirm account", emailService.buildEmailConfirm(user.getEmail(), link));

            user.setRefreshtoken("null");
            userRepository.save(user);
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
    public User getUserByResetPasswordToken(String resetpasswordtoken) {
       Optional<User> user = userRepository.findByresetpasswordtoken(resetpasswordtoken);
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
    public void handleRequestResetPassword(User user) {
        User user1 = getUser(user.getEmail());

        String resetPasswordToken = generateConfirmToken(user1.getEmail());
        user1.setResetpasswordtoken(resetPasswordToken);
        userRepository.save(user1);

        String link = "http://localhost:3000/reset-password/" + resetPasswordToken;
        emailService.sendEmail(user1.getEmail(), "Reset password account", emailService.buildEmailResetPassword(user1.getEmail(), link));
    }

    @Override
    public void handleResponseResetPassword(User user, String resetpasswordtoken) {
        User user1 = getUserByResetPasswordToken(resetpasswordtoken);
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }

    @Override
    public void setRefreshtoken(String email, String refreshtoken) {
        User user = getUser(email);
        user.setRefreshtoken(refreshtoken);
        userRepository.save(user);
    }

    @Override
    public User getUserByRefreshtoken(String refreshtoken) {
        Optional<User> user = userRepository.findByrefreshtoken(refreshtoken);
        return unwrapUser(user, 404L);
    }

    @Override
    public Boolean validateRefreshToken(String refreshtoken) {
        if(userRepository.existsByRefreshtoken(refreshtoken)) {
            try {
                String refreshtokenVerify = refreshtoken.replace(SecurityConstants.BEARER, "");
                String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY_REFRESH))
                .build()
                .verify(refreshtokenVerify)
                .getSubject();

                System.out.println(user);
                return true;
            }
            catch (JWTVerificationException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String generateAccessToken(String email) {
        String token = JWT.create()
        .withSubject(email)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
        .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        token = SecurityConstants.BEARER + token;
        return token;
    }

    @Override
    public String generateRefreshToken(String email) {
        String refreshtoken = JWT.create()
        .withSubject(email)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.REFRESH_TOKEN_EXPIRATION))
        .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY_REFRESH));
        refreshtoken =  SecurityConstants.BEARER + refreshtoken;
        return refreshtoken;
    }

    @Override
    public Long getIdUser(String email) {
        User user = getUser(email);
        return user.getId();
    }

    @Override
    public String getEmailById(Long id) {
        Optional<User> user = userRepository.findById(id);
        User user2 = unwrapUser(user, id);
        return user2.getEmail();
    }
    
    @Override
    public void deleteRefreshtoken(User user) {
        user.setRefreshtoken("null");
        userRepository.save(user);
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
