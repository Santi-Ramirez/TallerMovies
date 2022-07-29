package co.com.poli.bookingservice.service;

import co.com.poli.bookingservice.model.Showtime;
import co.com.poli.bookingservice.model.User;
import co.com.poli.bookingservice.persistence.entity.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> findAllBookings();

    Booking createBooking(Booking booking);

    Booking findBookingById(Long id);

    void deleteBookingById(Long id);

    List<Booking> findBookingsByUserId(Long userId);
}
