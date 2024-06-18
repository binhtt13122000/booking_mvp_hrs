package demo.booking.services.impl;

import demo.booking.common.Constants;
import demo.booking.entities.Booking;
import demo.booking.entities.Room;
import demo.booking.exception.*;
import demo.booking.projector.dto.BookingDto;
import demo.booking.projector.request.CreateBookingRequest;
import demo.booking.projector.request.UpdateBookingRequest;
import demo.booking.repositories.BookingRepository;
import demo.booking.repositories.RoomRepository;
import demo.booking.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    @Override
    public boolean isAvailable(Integer id, LocalDate checkoutTime, LocalDate checkinTime, Integer bookingId) {
        return bookingRepository.countAlreadyBooked(id, checkoutTime, checkinTime, bookingId) == 0;
    }

    @Override
    @Transactional
    public BookingDto createBooking(CreateBookingRequest request) throws BaseException {
        if (!checkValidTime(request.getCheckinTime(), request.getCheckoutTime())) {
            throw new InvalidTimeException();
        }
        Booking booking = CreateBookingRequest.convertToBooking(request);
        Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> {
            log.error("Room not found with id = {}", request.getRoomId());
            return new EntityNotFoundException(String.format("Room not found with id = %s", request.getRoomId()));
        });
        if (isAvailable(room.getId(), booking.getCheckoutTime().toLocalDate(), booking.getCheckinTime().toLocalDate(), 0)) {
            booking.setActive(true);
            booking.setRoomName(room.getName());
            booking.setRoom(room);
            Booking saveBooking = bookingRepository.save(booking);
            log.info("Booking is saved successfully with id = {}", saveBooking.getId());
            return BookingDto.fromDomain(saveBooking);
        }

        log.info("Room is booked with id = {}", room.getId());
        throw new BookedRoomException();
    }

    @Override
    public BookingDto cancelBooking(Integer id) throws BaseException {
        Booking booking = findBookingById(id);

        if (!booking.getActive()) {
            log.error("Booking is already deactivated with id = {}", id);
            throw new BookingAlreadyDeactivatedException();
        }

        booking.setActive(false);
        booking = bookingRepository.save(booking);
        log.error("Deactivated booking with id = {}", id);
        return BookingDto.fromDomain(booking);
    }

    @Override
    @Transactional
    public BookingDto updateBooking(UpdateBookingRequest request) throws BaseException {
        if (!checkValidTime(request.getCheckinTime(), request.getCheckoutTime())) {
            throw new InvalidTimeException();
        }

        Booking booking = UpdateBookingRequest.convertToBooking(request);
        Booking existBooking = findBookingById(request.getId());

        if (isAvailable(existBooking.getRoom().getId(), booking.getCheckoutTime().toLocalDate(), booking.getCheckinTime().toLocalDate(), booking.getId())) {
            existBooking.setCheckinTime(booking.getCheckinTime());
            existBooking.setCheckoutTime(booking.getCheckoutTime());
            existBooking.setGuestFirstname(booking.getGuestFirstname());
            existBooking.setGuestLastname(booking.getGuestLastname());
            existBooking = bookingRepository.save(existBooking);
            log.info("Booking is updated successfully with id = {}", existBooking.getId());
            return BookingDto.fromDomain(existBooking);
        }

        log.info("Room {} is booked in updated time", booking.getRoomName());
        throw new RoomNotAvailableException();
    }

    @Override
    public BookingDto findById(Integer id) throws BaseException {
        Booking booking = findBookingById(id);
        return BookingDto.fromDomain(booking);
    }

    @Override
    public Page<BookingDto> findBookings(int page, int size, String destination, Integer hotel) {
        if (Objects.isNull(hotel)) {
            return bookingRepository.findAllInRegion(destination, PageRequest.of(page, size)).map(BookingDto::fromDomain);
        }
        return bookingRepository.findAllByHotelInRegion(destination, hotel, PageRequest.of(page, size)).map(BookingDto::fromDomain);
    }

    private Booking findBookingById(Integer id) throws EntityNotFoundException {
        return bookingRepository.findById(id).orElseThrow(() -> {
            log.error("Booking is not found with id = {}", id);
            return new EntityNotFoundException(String.format("Booking not found with id = %s", id));
        });
    }

    private boolean checkValidTime(String checkIn, String checkOut) {
        LocalDateTime checkInTime = LocalDateTime.parse(checkIn, Constants.DATE_TIME_FORMATTER);
        LocalDateTime checkOutTime = LocalDateTime.parse(checkOut, Constants.DATE_TIME_FORMATTER);
        return checkInTime.isAfter(LocalDateTime.now()) && checkOutTime.isAfter(checkInTime);
    }
}
