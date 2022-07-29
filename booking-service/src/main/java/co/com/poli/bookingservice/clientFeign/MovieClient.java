package co.com.poli.bookingservice.clientFeign;

import co.com.poli.bookingservice.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-service", fallback = MovieClientImplHystrixFallback.class)
public interface MovieClient {

    @GetMapping("/taller-movies/v1/movies")
    Response findAllMovies();

    @GetMapping("/taller-movies/v1/movies/{id}")
    Response findMovieById(@PathVariable("id") Long id);
}
