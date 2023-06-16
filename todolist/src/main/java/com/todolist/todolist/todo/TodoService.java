package com.todolist.todolist.todo;

import java.util.List;

public interface TodoService {
    List<Todo> getTodo(Long userId);
    Todo saveTodo(Todo todo, Long userId);
    Todo markImportantTodo(Long userId, Long todoId);
    Todo undoImportantTodo(Long userId, Long todoId);
    void deleteTodo(Long todoId);
    Todo updateTodo(String task, Long todoId);
    Todo markDoneTodo(Long todoId);
    Todo undoDoneTodo(Long todoId);
}
