package by.hackaton.bookcrossing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String email;
    private String password;
}
