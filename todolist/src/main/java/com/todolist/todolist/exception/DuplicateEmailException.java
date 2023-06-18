package com.todolist.todolist.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String email) {
        super("The email is: '" + email + "' is haved duplicated!");
    }
}
