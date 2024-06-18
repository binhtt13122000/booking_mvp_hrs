package demo.booking.repositories;

import demo.booking.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT count(1)   " +
            "        FROM bookings   " +
            "        WHERE room_id = :roomId  " +
            "        AND checkin_time <= :checkoutTime  " +
            "        AND checkout_time >= :checkinTime  " +
            "        AND active = true AND id != :bookingId", nativeQuery = true)
    int countAlreadyBooked(@Param("roomId") long roomId, @Param("checkoutTime") LocalDate checkoutTime, @Param("checkinTime") LocalDate checkinTime, @Param("bookingId") long bookingId);

    @Query(value = "SELECT count(1)   " +
            "        FROM Bookings   " +
            "        WHERE id = :bookingId  " +
            "        AND guest_id = :guestId " +
            "        AND active = true  ", nativeQuery = true)
    int existByIdAndGuestId(@Param("bookingId") Integer bookingId, @Param("guestId") Integer guestId);

    @Query("SELECT b FROM Booking b WHERE b.room.hotel.destination = :destination and b.room.hotel.id = :hotel and b.active = true")
    Page<Booking> findAllByHotelInRegion(String destination, Integer hotel, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.room.hotel.destination = :destination and b.active = true")
    Page<Booking> findAllInRegion(String destination, Pageable pageable);
}
