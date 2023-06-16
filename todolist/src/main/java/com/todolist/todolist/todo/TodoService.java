package com.todolist.todolist.todo;

import java.util.List;

public interface TodoService {
    List<Todo> getTodo(Long userId);
    Todo saveTodo(Todo todo, Long userId);
}
