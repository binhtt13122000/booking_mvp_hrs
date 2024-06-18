package demo.booking.exception;

import lombok.NonNull;

public class RoomNotAvailableException extends BaseException {
    private static final String MESSAGE = "Room is not available on this time";
    private static final String CODE = "ROOM_NOT_AVAILABLE_EXCEPTION";

    public RoomNotAvailableException(@NonNull String message, @NonNull String code) {
        super(message, code);
    }

    public RoomNotAvailableException() {
        super(MESSAGE, CODE);
    }

    public RoomNotAvailableException(@NonNull String message) {
        super(message, CODE);
    }
}
