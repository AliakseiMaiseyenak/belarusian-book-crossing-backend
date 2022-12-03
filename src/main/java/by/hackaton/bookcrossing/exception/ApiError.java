package by.hackaton.bookcrossing.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ApiError {
    @JsonIgnore
    HttpStatus status;
    String message;
    String context;
    String debugMessage;

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, String context, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.context = context;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
