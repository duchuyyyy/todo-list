package com.todolist.todolist.todo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.todolist.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "task", nullable = false)
    private String task;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "important", nullable = false)
    private Boolean important;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void loadFromDto(TodoDto dto) {
        this.task = dto.getTask();
    }
}
