package by.hackaton.bookcrossing.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class EmailConfirmationException extends ResponseStatusException {

    public EmailConfirmationException(HttpStatus status) {
        super(status);
    }

    public EmailConfirmationException(HttpStatus status, @Nullable String reason) {
        super(status, reason);
    }
}
