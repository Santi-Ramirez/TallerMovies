package co.com.poli.showtimeservice.clientFeign;

import co.com.poli.showtimeservice.helpers.Response;
import co.com.poli.showtimeservice.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "movie-service")
public interface MovieClient {

    @GetMapping("/taller-movies/v1/movies")
    List<Movie> findAllMovies();

    @GetMapping("/taller-movies/v1/movies/{id}")
    Response findMovieById(@PathVariable("id") Long id);
}
