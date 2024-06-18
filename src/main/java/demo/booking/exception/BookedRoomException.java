package demo.booking.exception;

import lombok.NonNull;

public class BookedRoomException extends BaseException {
    private static final String MESSAGE = "This room is booked on this time";
    private static final String CODE = "ROOM_IS_BOOKED_EXCEPTION";

    public BookedRoomException(@NonNull String message, @NonNull String code) {
        super(message, code);
    }

    public BookedRoomException() {
        super(MESSAGE, CODE);
    }

    public BookedRoomException(@NonNull String message) {
        super(message, CODE);
    }
}
