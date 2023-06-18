package com.todolist.todolist.user;

public interface UserService {
    User saveUser(User user);
    User getUser(String email);
}
