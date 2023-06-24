package com.todolist.todolist.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
    @PostMapping("/error")
    public ResponseEntity<HttpStatus> handleError() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
