package by.hackaton.bookcrossing.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class SubscriptionRequest {
    @Email
    private String email;
}
