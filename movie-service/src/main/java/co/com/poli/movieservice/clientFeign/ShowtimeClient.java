package co.com.poli.movieservice.clientFeign;

import co.com.poli.movieservice.model.Showtime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "showtime-service")
public interface ShowtimeClient {

    @GetMapping("/taller-movies/v1/showtimes")
    List<Showtime> findAllShowTimes();

}
