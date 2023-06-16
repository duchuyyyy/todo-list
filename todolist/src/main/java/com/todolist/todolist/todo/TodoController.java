package com.todolist.todolist.todo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {
    
    TodoService todoService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Todo>> getTodo(@PathVariable Long userId) {
        return new ResponseEntity<>(todoService.getTodo(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Todo> saveTodo(@RequestBody Todo todo, @PathVariable Long userId) {
        return new ResponseEntity<>(todoService.saveTodo(todo, userId), HttpStatus.CREATED);
    } 
}
