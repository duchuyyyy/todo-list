package com.todolist.todolist.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolist.todolist.exception.DuplicateEmailException;
import com.todolist.todolist.exception.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        user.setVerification(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Boolean check = userRepository.existsByEmail(user.getEmail());
        if(check == false) {
            return userRepository.save(user);
        }
        else {
            throw new DuplicateEmailException(user.getEmail());
        }
    }

    public static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
}
