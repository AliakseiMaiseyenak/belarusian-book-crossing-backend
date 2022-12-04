package by.hackaton.bookcrossing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class PasswordRequest {
    @Email
    private String email;
    private String password;
    private String code;
}
