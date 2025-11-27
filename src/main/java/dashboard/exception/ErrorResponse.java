package dashboard.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> message;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, List<String> message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this(timestamp, status, error, List.of(message), path);
    }
}
