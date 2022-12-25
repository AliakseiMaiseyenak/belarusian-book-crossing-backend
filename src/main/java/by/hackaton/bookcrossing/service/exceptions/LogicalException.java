package by.hackaton.bookcrossing.service.exceptions;

import java.text.MessageFormat;

public class LogicalException extends RuntimeException {

    private int statusCode;

    public LogicalException(ServerError serverError) {
        super(serverError.getMessage());
        this.statusCode = serverError.getStatus();
    }

    public LogicalException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, arguments));
        this.statusCode = ServerError.INTERNAL_SERVER_ERROR.getStatus();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
