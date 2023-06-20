package com.todolist.todolist.todo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.todolist.todolist.exception.EntityNotFoundException;
import com.todolist.todolist.exception.UserNotHaveTodoException;
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
       todo.setStatus(false);
       todo.setImportant(false);
       return todoRepository.save(todo);
    }

    @Override
    public Todo markImportantTodo(Long userId, Long todoId) {
        User user = UserServiceImpl.unwrapUser(userRepository.findById(userId), userId);
        Todo todo = unwrapTodo(todoRepository.findById(todoId), todoId);
        if(user.getId() != todo.getUser().getId()) {
            throw new UserNotHaveTodoException(userId, todoId);
        } 
        todo.setImportant(true);
        return todoRepository.save(todo);
    }

    @Override
    public Todo undoImportantTodo(Long userId, Long todoId) {
         User user = UserServiceImpl.unwrapUser(userRepository.findById(userId), userId);
         Todo todo = unwrapTodo(todoRepository.findById(todoId), todoId);
         if(user.getId() != todo.getUser().getId()) {
             throw new UserNotHaveTodoException(userId, todoId);
         } 
         todo.setImportant(false);
         return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long todoId) {
       todoRepository.deleteById(todoId);
    }

    @Override
    public Todo updateTodo(String task, Long todoId) {
        Todo todo = unwrapTodo(todoRepository.findById(todoId), todoId);
        todo.setTask(task);
        return todoRepository.save(todo);
    }

    @Override
    public Todo markDoneTodo(Long todoId) {
         Todo todo = unwrapTodo(todoRepository.findById(todoId), todoId);
         todo.setStatus(true);
         return todoRepository.save(todo);
    }

    @Override
    public Todo undoDoneTodo(Long todoId) {
         Todo todo = unwrapTodo(todoRepository.findById(todoId), todoId);
         todo.setStatus(false);
         return todoRepository.save(todo);
    }

    public static Todo unwrapTodo(Optional<Todo> entity, Long id) {
      if (entity.isPresent()) return entity.get();
      else throw new EntityNotFoundException(id, Todo.class);
  }
}
