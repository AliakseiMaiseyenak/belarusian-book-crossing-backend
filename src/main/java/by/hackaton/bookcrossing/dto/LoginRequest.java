package by.hackaton.bookcrossing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class LoginRequest {
    private String username;
    @Email
    private String email;
    private String password;
}
