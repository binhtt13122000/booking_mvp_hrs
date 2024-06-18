package demo.booking.projector.request;

import demo.booking.common.Constants;
import demo.booking.entities.Booking;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateBookingRequest {
    @NotNull
    private Integer id;
    @NotNull
    private String guestLastName;
    @NotNull
    private String guestFirstName;
    @NotNull
    private String checkinTime;
    @NotNull
    private String checkoutTime;
    private String note;

    public static Booking convertToBooking(UpdateBookingRequest request) {
        return Booking
                .builder()
                .id(request.getId())
                .checkoutTime(LocalDateTime.parse(request.getCheckoutTime(), Constants.DATE_TIME_FORMATTER))
                .checkinTime(LocalDateTime.parse(request.getCheckinTime(), Constants.DATE_TIME_FORMATTER))
                .guestFirstname(request.getGuestFirstName())
                .guestLastname(request.getGuestLastName())
                .note(request.getNote())
                .build();
    }
}
