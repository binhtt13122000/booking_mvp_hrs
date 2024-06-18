package demo.booking.controllers;

import demo.booking.common.Constants;
import demo.booking.exception.BaseException;
import demo.booking.projector.dto.BookingDto;
import demo.booking.projector.request.CreateBookingRequest;
import demo.booking.projector.request.UpdateBookingRequest;
import demo.booking.projector.response.BaseResponse;
import demo.booking.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/booking")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<BaseResponse<Page<BookingDto>>> getBookings(@RequestParam(name = "offset", defaultValue = Constants.DEFAULT_OFFSET) Integer offset,
                                                                      @RequestParam(name = "limit", defaultValue = Constants.DEFAULT_LIMIT) Integer limit,
                                                                      @RequestParam(name = "region", required = false) String region,
                                                                      @RequestParam(name = "hotel_id", required = false) Integer hotelId) {

        Page<BookingDto> bookingPage = bookingService.findBookings(offset, limit, region, hotelId);
        return BaseResponse.buildOkResponse(bookingPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<BookingDto>> getBookingById(@PathVariable int id) throws BaseException {
        BookingDto bookingDto = bookingService.findById(id);
        return BaseResponse.buildOkResponse(bookingDto);
    }


    @PostMapping
    public ResponseEntity<BaseResponse<BookingDto>> createBooking(@RequestBody @Valid CreateBookingRequest request) throws BaseException {
        BookingDto bookingDto = bookingService.createBooking(request);
        return BaseResponse.buildCreatedResponse(bookingDto);
    }

    @PutMapping
    public ResponseEntity<BaseResponse<BookingDto>> updateBooking(@RequestBody @Valid UpdateBookingRequest request) throws BaseException {
        BookingDto bookingDto = bookingService.updateBooking(request);
        return BaseResponse.buildOkResponse(bookingDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<BookingDto>> cancelBooking(@PathVariable("id") int id) throws BaseException {
        BookingDto bookingDto = bookingService.cancelBooking(id);
        return BaseResponse.buildOkResponse(bookingDto);
    }
}
