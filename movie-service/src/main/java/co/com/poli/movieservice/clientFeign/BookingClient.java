package co.com.poli.movieservice.clientFeign;

import co.com.poli.movieservice.model.Booking;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "booking-service")
public interface BookingClient {

    @GetMapping("/taller-movies/v1/bookings")
    List<Booking> findAllBookings();

}
