package demo.booking.exception;

import lombok.NonNull;

public class BookingAlreadyDeactivatedException extends BaseException {
    private static final String MESSAGE = "This room is already deactivated.";
    private static final String CODE = "ROOM_IS_ALREADY_DEACTIVATED";

    public BookingAlreadyDeactivatedException(@NonNull String message, @NonNull String code) {
        super(message, code);
    }

    public BookingAlreadyDeactivatedException() {
        super(MESSAGE, CODE);
    }

    public BookingAlreadyDeactivatedException(@NonNull String message) {
        super(message, CODE);
    }
}
