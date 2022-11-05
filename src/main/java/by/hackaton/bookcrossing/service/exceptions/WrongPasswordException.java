package by.hackaton.bookcrossing.service.exceptions;

import java.text.MessageFormat;

public class WrongPasswordException extends RuntimeException {

    private int statusCode;

    public WrongPasswordException(ServerError serverError) {
        super(serverError.getMessage());
        this.statusCode = serverError.getStatusCode();
    }

    public WrongPasswordException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
        this.statusCode = ServerError.WRONG_PASSWORD.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
