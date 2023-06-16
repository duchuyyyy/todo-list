package com.todolist.todolist.todo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long>{
    List<Todo> findByUserId(Long userId);
}
