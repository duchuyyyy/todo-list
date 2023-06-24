package com.todolist.todolist.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long>{
    Boolean existsByEmail(String email);
    Boolean existsByRefreshtoken(String refreshtoken);
    Optional<User> findByEmail(String email);
    Optional<User> findByConfirmtoken(String confirmtoken);
    Optional<User> findByresetpasswordtoken(String resetpasswordtoken);
    Optional<User> findByrefreshtoken(String refreshtoken);
}
