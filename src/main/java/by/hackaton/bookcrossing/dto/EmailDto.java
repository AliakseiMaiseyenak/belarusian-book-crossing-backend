package by.hackaton.bookcrossing.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class EmailDto {
    @Email
    private String email;
}
