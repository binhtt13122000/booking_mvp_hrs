package demo.booking.projector.dto;

import demo.booking.entities.Booking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingDto {
    private Integer id;
    private Integer roomId;
    private String roomName;
    private String guestUsername;
    private String guestLastname;
    private String guestFirstname;
    private String checkinTime;
    private String checkoutTime;
    private String createdAt;
    private String updateAt;
    private String note;
    private boolean active;

    public static BookingDto fromDomain(Booking booking) {
        return BookingDto
                .builder()
                .id(booking.getId())
                .active(booking.getActive())
                .roomId(booking.getRoom().getId())
                .roomName(booking.getRoomName())
                .guestUsername(booking.getGuestUsername())
                .guestFirstname(booking.getGuestFirstname())
                .guestLastname(booking.getGuestLastname())
                .checkinTime(booking.getCheckinTime().toString())
                .checkoutTime(booking.getCheckoutTime().toString())
                .createdAt(booking.getCreatedAt().toString())
                .updateAt(booking.getUpdatedAt().toString())
                .note(booking.getNote())
                .build();
    }
}
