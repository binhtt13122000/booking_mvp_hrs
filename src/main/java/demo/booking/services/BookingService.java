package demo.booking.services;

import demo.booking.exception.BaseException;
import demo.booking.projector.dto.BookingDto;
import demo.booking.projector.request.CreateBookingRequest;
import demo.booking.projector.request.UpdateBookingRequest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BookingService {
    boolean isAvailable(Integer id, LocalDate checkoutTime, LocalDate checkinTime, Integer bookingId);

    BookingDto createBooking(CreateBookingRequest request) throws BaseException;

    BookingDto cancelBooking(Integer id) throws BaseException;

    BookingDto updateBooking(UpdateBookingRequest request) throws BaseException;

    BookingDto findById(Integer id) throws BaseException;

    Page<BookingDto> findBookings(int page, int size, String destination, Integer hotel);
}
