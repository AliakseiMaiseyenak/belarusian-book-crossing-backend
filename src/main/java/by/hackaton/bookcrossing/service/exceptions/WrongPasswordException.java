package by.hackaton.bookcrossing.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class WrongPasswordException extends ResponseStatusException {

    public WrongPasswordException(HttpStatus status) {
        super(status);
    }

    public WrongPasswordException(HttpStatus status, @Nullable String reason) {
        super(status, reason);
    }
}
