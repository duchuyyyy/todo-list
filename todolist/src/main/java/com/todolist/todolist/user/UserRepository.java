package com.todolist.todolist.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
    Boolean existsByEmail(String email);
}
