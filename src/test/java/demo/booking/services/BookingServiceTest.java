package demo.booking.services;

import demo.booking.entities.Booking;
import demo.booking.entities.Room;
import demo.booking.exception.BaseException;
import demo.booking.exception.BookedRoomException;
import demo.booking.exception.EntityNotFoundException;
import demo.booking.exception.InvalidTimeException;
import demo.booking.projector.request.CreateBookingRequest;
import demo.booking.repositories.BookingRepository;
import demo.booking.repositories.RoomRepository;
import demo.booking.services.impl.BookingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    @Spy
    private BookingServiceImpl bookingService;

    @Test
    public void shouldErrorWhenCreateBookingWithCheckInTimeIsBeforeNow() {
        CreateBookingRequest createBookingRequest =
                CreateBookingRequest.builder()
                        .checkinTime("2023-06-10 13:00")
                        .checkoutTime("2023-06-11 13:00")
                        .build();

        Assertions.assertThrows(InvalidTimeException.class, () -> bookingService.createBooking(createBookingRequest));
    }

    @Test
    public void shouldErrorWhenCreateBookingWithCheckOutTimeBeforeCheckInTime() {
        CreateBookingRequest createBookingRequest =
                CreateBookingRequest.builder()
                        .checkinTime("2023-06-13 13:00")
                        .checkoutTime("2023-06-12 13:00")
                        .build();

        Assertions.assertThrows(InvalidTimeException.class, () -> bookingService.createBooking(createBookingRequest));
    }

    @Test
    public void shouldErrorWhenRoomNotFound() {
        CreateBookingRequest createBookingRequest =
                CreateBookingRequest.builder()
                        .roomId(1)
                        .checkinTime("2024-06-12 13:00")
                        .checkoutTime("2024-06-13 13:00")
                        .build();

        Mockito.when(roomRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookingService.createBooking(createBookingRequest));
    }

    @Test
    public void shouldErrorWhenRoomIsBooked() {
        CreateBookingRequest createBookingRequest =
                CreateBookingRequest.builder()
                        .roomId(1)
                        .checkinTime("2024-06-12 13:00")
                        .checkoutTime("2024-06-13 13:00")
                        .build();

        Mockito.doReturn(Optional.of(Room.builder().id(1).name("name").build())).when(roomRepository).findById(Mockito.anyInt());
        Mockito.doReturn(false).when(bookingService).isAvailable(Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt());
        Assertions.assertThrows(BookedRoomException.class, () -> bookingService.createBooking(createBookingRequest));
    }

    @Test
    public void shouldSaveSuccess() throws BaseException {
        CreateBookingRequest createBookingRequest =
                CreateBookingRequest.builder()
                        .roomId(1)
                        .checkinTime("2024-06-12 13:00")
                        .checkoutTime("2024-06-13 13:00")
                        .build();

        Room room = Room.builder().id(1).name("name").build();
        Booking booking =
                Booking.builder()
                        .id(1)
                        .room(room)
                        .active(true)
                        .checkinTime(LocalDateTime.now())
                        .checkoutTime(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
        Mockito.doReturn(Optional.of(room)).when(roomRepository).findById(Mockito.anyInt());
        Mockito.doReturn(true).when(bookingService).isAvailable(Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt());
        Mockito.doReturn(booking).when(bookingRepository).save(Mockito.any());

        Assertions.assertEquals(bookingService.createBooking(createBookingRequest).getId(), booking.getId());
        Mockito.verify(bookingService, Mockito.times(1)).createBooking(createBookingRequest);
    }
}
