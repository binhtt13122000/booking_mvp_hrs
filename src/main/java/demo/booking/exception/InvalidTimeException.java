package demo.booking.exception;

import lombok.NonNull;

public class InvalidTimeException extends BaseException {
    private static final String MESSAGE = "Invalid time exception";
    private static final String CODE = "INVALID_TIME_EXCEPTION";

    public InvalidTimeException(@NonNull String message, @NonNull String code) {
        super(message, code);
    }

    public InvalidTimeException() {
        super(MESSAGE, CODE);
    }

    public InvalidTimeException(@NonNull String message) {
        super(message, CODE);
    }
}
