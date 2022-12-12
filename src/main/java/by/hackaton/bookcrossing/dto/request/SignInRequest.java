package by.hackaton.bookcrossing.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignInRequest {
    @NotNull
    private String username;
    @Email
    private String email;
    @NotNull
    private String password;
}
