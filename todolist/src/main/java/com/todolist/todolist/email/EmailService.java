package com.todolist.todolist.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    String buildEmailConfirm(String name, String link);
    String buildEmailResetPassword(String name, String link);
}
