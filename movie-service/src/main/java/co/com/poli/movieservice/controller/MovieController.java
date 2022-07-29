package co.com.poli.movieservice.controller;

import co.com.poli.movieservice.helpers.ErrorMessage;
import co.com.poli.movieservice.helpers.Response;
import co.com.poli.movieservice.helpers.ResponseBuild;
import co.com.poli.movieservice.persistence.entity.Movie;
import co.com.poli.movieservice.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final ResponseBuild builder;

    @PostMapping
    public Response createMovie(@Valid @RequestBody Movie movie, BindingResult result){
        if(result.hasErrors()){
            return builder.badRequest(formatMessage(result));
        }
        Movie movie1 = movieService.createMovie(movie);
        return builder.createSuccess(movie1);
    }

    @GetMapping("/{id}")
    public Response findMovieById(@PathVariable("id") Long id) {
        Movie movie = movieService.findMovieById(id);
        if(movie == null){
            return builder.notFound();
        }
        return builder.success(movie);
    }

    @GetMapping
    public List<Movie> findAllMovies() {
        return movieService.findAllMovies();
    }

    @DeleteMapping("/{id}")
    public Response deleteMovieById(@PathVariable("id") Long id) {
        Movie movie = movieService.findMovieById(id);
        if(movie == null){
            return builder.notFound();
        }
        movieService.deleteMovieById(id);
        return builder.success(movie);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put(error.getField(), error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .error(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
