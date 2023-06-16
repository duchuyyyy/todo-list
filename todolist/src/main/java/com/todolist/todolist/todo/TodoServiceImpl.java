package com.todolist.todolist.todo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todolist.todolist.user.User;
import com.todolist.todolist.user.UserRepository;
import com.todolist.todolist.user.UserServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService{
    
    TodoRepository todoRepository;
    UserRepository userRepository;

    @Override
    public List<Todo> getTodo(Long userId) {
       return todoRepository.findByUserId(userId);
    }

    @Override
    public Todo saveTodo(Todo todo, Long userId) {
       User user = UserServiceImpl.unwrapUser(userRepository.findById(userId), userId);
       todo.setUser(user);
       return todoRepository.save(todo);
    }
}
