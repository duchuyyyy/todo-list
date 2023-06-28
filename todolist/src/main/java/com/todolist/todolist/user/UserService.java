package com.todolist.todolist.user;


public interface UserService {
    void saveUser(User user);
    User getUser(String email);
    String generateConfirmToken(String email);
    User getUserByConfirmToken(String comfirmtoken);
    String confirmToken(String confirmtoken);
    void handleRequestResetPassword(User user);
    void handleResponseResetPassword(User user, String resetpasswordtoken);
    User getUserByResetPasswordToken(String resetpasswordtoken);
    void setRefreshtoken(String email, String refreshtoken);
    Long getIdUser(String email);
    Boolean validateRefreshToken(String refreshtoken);
    String generateAccessToken(String email);
    String generateRefreshToken(String email);
    User getUserByRefreshtoken(String refreshtoken);
    void deleteRefreshtoken(User user);
    String getEmailById(Long id);
}
