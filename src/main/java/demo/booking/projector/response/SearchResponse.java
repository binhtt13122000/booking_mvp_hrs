package demo.booking.projector.response;

import demo.booking.projector.dto.BookingDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
    private List<BookingDto> bookings;
    private int page;
    private int size;
}
