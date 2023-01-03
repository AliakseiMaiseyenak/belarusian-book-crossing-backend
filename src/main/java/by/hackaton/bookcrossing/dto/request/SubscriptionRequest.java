package by.hackaton.bookcrossing.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class SubscriptionRequest {
    @Email
    private String email;
}
