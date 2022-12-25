package by.hackaton.bookcrossing.service.exceptions;

public enum ServerError {
    EMAIL_ALREADY_EXISTS(1002, "User with email already exists"),
    EMAIL_NOT_CONFIRMED(1003, "Email not confirmed"),
    WRONG_PASSWORD(404, "Wrong password"),
    ENTITY_NOT_FOUND(1004, "Not found"),
    INTERNAL_SERVER_ERROR(5001, "Internal server error");

    private int status;
    private String message;

    ServerError(int status, String msg) {
        this.message = msg;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
