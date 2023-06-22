package com.todolist.todolist.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.todolist.dto.Request.RefreshTokenRequestDto;
import com.todolist.todolist.dto.Request.ResetPasswordRequestDto;
import com.todolist.todolist.dto.Response.ResetPasswordResponseDto;
import com.todolist.todolist.todo.Todo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "verification", nullable = false)
    private Boolean verification;

    @Column(name = "confirmtoken", nullable = false)
    private String confirmtoken;

    @Column(name = "resetpasswordtoken", nullable = true)
    private String resetpasswordtoken;

    @Column(name = "refreshtoken", nullable = false)
    private String refreshtoken;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Todo> todos;

    public void loadFromDto(UserDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
    }

    public void loadFromResetPasswordRequestDto(ResetPasswordRequestDto resetPasswordRequestDto) {
        this.email = resetPasswordRequestDto.getEmail();
    }

    public void loadFromResetPasswordResponseDto(ResetPasswordResponseDto resetPasswordResponseDto) {
        this.password = resetPasswordResponseDto.getPassword();
    }

    public void loadFromRefreshTokenRequestDto(RefreshTokenRequestDto refreshTokenRequestDto) {
        this.refreshtoken = refreshTokenRequestDto.getRefreshtoken();
    }
}
