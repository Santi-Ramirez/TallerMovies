package co.com.poli.userservice.clientFeign;

import co.com.poli.userservice.helpers.Response;
import co.com.poli.userservice.model.Booking;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "booking-service")
public interface BookingClient {

    @GetMapping("/taller-movies/v1/bookings/userId/{userId}")
    List<Booking> findBookingsByUserId(@PathVariable("userId") Long userId);
}
