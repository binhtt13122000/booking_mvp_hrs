package demo.booking.exception;

import lombok.NonNull;

public class EntityNotFoundException extends BaseException {
    private static final String MESSAGE = "Entity could not be found in the system";
    private static final String CODE = "ENTITY_NOT_FOUND_EXCEPTION";

    public EntityNotFoundException(@NonNull String message, @NonNull String code) {
        super(message, code);
    }

    public EntityNotFoundException() {
        super(MESSAGE, CODE);
    }

    public EntityNotFoundException(@NonNull String message) {
        super(message, CODE);
    }
}
