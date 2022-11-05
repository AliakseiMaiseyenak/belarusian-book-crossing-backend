package by.hackaton.bookcrossing.service.exceptions;

import java.text.MessageFormat;

public class EmailConfirmationException extends RuntimeException {

    private int statusCode;

    public EmailConfirmationException(ServerError serverError) {
        super(serverError.getMessage());
        this.statusCode = serverError.getStatusCode();
    }

    public EmailConfirmationException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
        this.statusCode = ServerError.EMAIL_NOT_CONFIRMED.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
