package com.todolist.todolist.user;
import lombok.*;

@Data
public class UserDto {
    
    private String email;
    private String password;

    public void loadFromEntity(User entity) {
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }
}
