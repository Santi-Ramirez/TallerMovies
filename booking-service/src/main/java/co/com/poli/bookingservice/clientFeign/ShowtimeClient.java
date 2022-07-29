package co.com.poli.bookingservice.clientFeign;

import co.com.poli.bookingservice.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "showtime-service", fallback = ShowtimeClientImplHystrixFallback.class)
public interface ShowtimeClient {

    @GetMapping("/taller-movies/v1/showtimes/{idShow}")
    Response findShowTimeById(@PathVariable("idShow") Long id);

}
