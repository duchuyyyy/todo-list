package com.todolist.todolist.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
