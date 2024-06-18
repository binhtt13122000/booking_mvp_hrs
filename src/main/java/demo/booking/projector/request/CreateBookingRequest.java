package demo.booking.projector.request;

import demo.booking.common.Constants;
import demo.booking.entities.Booking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateBookingRequest {
    @NotNull
    private Integer roomId;
    @NotNull
    private String guestUserName;
    @NotNull
    private String guestLastName;
    @NotNull
    private String guestFirstName;
    @NotNull
    private String checkinTime;
    @NotNull
    private String checkoutTime;
    private String note;

    public static Booking convertToBooking(CreateBookingRequest request) {
        return Booking
                .builder()
                .checkoutTime(LocalDateTime.parse(request.getCheckoutTime(), Constants.DATE_TIME_FORMATTER))
                .checkinTime(LocalDateTime.parse(request.getCheckinTime(), Constants.DATE_TIME_FORMATTER))
                .guestFirstname(request.getGuestFirstName())
                .guestLastname(request.getGuestLastName())
                .guestUsername(request.getGuestUserName())
                .note(request.getNote())
                .build();
    }
}
