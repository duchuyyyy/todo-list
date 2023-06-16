package com.todolist.todolist.exception;

public class UserNotHaveTodoException extends RuntimeException{
    public UserNotHaveTodoException(Long userId, Long todoId) {
        super("The user with id: '" + userId + "' is not haved todo with id: '" + todoId);
    }
}
