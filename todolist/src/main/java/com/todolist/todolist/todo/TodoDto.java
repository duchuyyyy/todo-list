package com.todolist.todolist.todo;
import lombok.*;

@Data
public class TodoDto {
    private String task;

    public void loadFromEntity(Todo entity) {
        this.task = entity.getTask();
    }
}
