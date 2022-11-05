package by.hackaton.bookcrossing.service.exceptions;

public enum ServerError {
    EMAIL_ALREADY_EXISTS(1002, "User with email already exists"),
    EMAIL_NOT_CONFIRMED(1003, "Email not confirmed"),
    WRONG_PASSWORD(1004, "Wrong password"),
    INTERNAL_SERVER_ERROR(5001, "Internal server error");

    private int statusCode;
    private String message;

    ServerError(int statusCode, String msg) {
        this.message = msg;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
