package com.todolist.todolist.user;


public interface UserService {
    User saveUser(User user);
    User getUser(String email);
    String generateConfirmToken(String email);
    User getUserByConfirmToken(String comfirmtoken);
    String confirmToken(String confirmtoken);
}
