package by.hackaton.bookcrossing.service.exceptions;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {

    private int statusCode;

    public EntityNotFoundException(ServerError serverError) {
        super(serverError.getMessage());
        this.statusCode = serverError.getStatus();
    }

    public EntityNotFoundException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
        this.statusCode = ServerError.ENTITY_NOT_FOUND.getStatus();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
