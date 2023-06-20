package com.todolist.todolist.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    String buildEmail(String name, String link);
}
