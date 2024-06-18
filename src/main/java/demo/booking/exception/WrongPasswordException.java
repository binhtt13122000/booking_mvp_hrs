package demo.booking.exception;

import lombok.NonNull;

public class WrongPasswordException extends BaseException {
    private static final String MESSAGE = "Wrong password exception";
    private static final String CODE = "WRONG_PASSWORD_EXCEPTION";

    public WrongPasswordException(@NonNull String message, @NonNull String code) {
        super(message, code);
    }

    public WrongPasswordException() {
        super(MESSAGE, CODE);
    }

    public WrongPasswordException(@NonNull String message) {
        super(message, CODE);
    }
}
