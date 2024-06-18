package demo.booking.exception;

import lombok.Getter;
import lombok.NonNull;
import software.amazon.awssdk.annotations.NotNull;

@Getter
public abstract class BaseException extends Exception {
    @NotNull
    private final String message;
    @NotNull
    private final String code;

    public BaseException(@NonNull final String message, @NonNull final String code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
