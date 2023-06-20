package com.todolist.todolist.todo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/todo/{userId}")
public class TodoController {
    
    TodoService todoService;

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getTodo(@PathVariable Long userId) {
        return new ResponseEntity<>(todoService.getTodo(userId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Todo> saveTodo(@RequestBody TodoDto todoDto, @PathVariable Long userId) {
        Todo todo = new Todo();
        todo.loadFromDto(todoDto);
        return new ResponseEntity<>(todoService.saveTodo(todo, userId), HttpStatus.CREATED);
    }
    
    @PutMapping("/{todoId}/important")
    public ResponseEntity<Todo> markImportantTodo(@PathVariable Long userId, @PathVariable Long todoId) {
        return new ResponseEntity<>(todoService.markImportantTodo(userId, todoId), HttpStatus.OK);
    }

    @PutMapping("/{todoId}/undoimportant")
    public ResponseEntity<Todo> undoImportantTodo(@PathVariable Long userId, @PathVariable Long todoId) {
        return new ResponseEntity<>(todoService.undoImportantTodo(userId, todoId), HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{todoId}/update") 
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoDto todoDto, @PathVariable Long todoId) {
        Todo todo = new Todo();
        todo.loadFromDto(todoDto);
        return new ResponseEntity<>(todoService.updateTodo(todo.getTask(), todoId) , HttpStatus.OK);
    }

    @PutMapping("/{todoId}/done")
    public ResponseEntity<Todo> markDoneTodo(@PathVariable Long todoId) {
        return new ResponseEntity<>(todoService.markDoneTodo(todoId), HttpStatus.OK);
    }

    @PutMapping("/{todoId}/undodone")
    public ResponseEntity<Todo> undoDoneTodo(@PathVariable Long todoId) {
        return new ResponseEntity<>(todoService.undoDoneTodo(todoId), HttpStatus.OK);
    }
}
