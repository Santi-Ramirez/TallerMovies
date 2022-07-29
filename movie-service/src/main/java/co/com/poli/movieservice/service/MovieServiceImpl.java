package co.com.poli.movieservice.service;

import co.com.poli.movieservice.clientFeign.BookingClient;
import co.com.poli.movieservice.clientFeign.ShowtimeClient;
import co.com.poli.movieservice.exceptions.TodoExceptions;
import co.com.poli.movieservice.model.Booking;
import co.com.poli.movieservice.model.Showtime;
import co.com.poli.movieservice.persistence.entity.Movie;
import co.com.poli.movieservice.persistence.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ShowtimeClient showtimeClient;

    private final BookingClient bookingClient;

    @Override
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie findMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMovieById(Long id) {

        List<Showtime> showtimesClient = showtimeClient.findAllShowTimes();
        List<Showtime> showtimes = showtimesClient.stream().map(showtime -> {
            Long[] ids = showtime.getMoviesIds();
            List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
            List<Movie> moviesExist = idsMovies.stream().map(movieId -> {
                Movie movie = findMovieById(movieId);
                if(movie.getId() == id)
                    throw new TodoExceptions("No se puede eliminar la Movie porque tiene Programaciones asociadas", HttpStatus.BAD_REQUEST);
                else
                    return movie;
            }).collect(Collectors.toList());
            return showtime;
        }).collect(Collectors.toList());

        List<Booking> bookingsClient = bookingClient.findAllBookings();
        List<Booking> bookings = bookingsClient.stream().map(booking -> {
            Long[] ids = booking.getMoviesIds();
            List<Movie> idsMovies = new ArrayList<>(Arrays.asList(ids)).stream().map(movieId -> {
            //List<Movie> moviesExist = idsMovies.stream().map(movieId -> {
                Movie movie = findMovieById(movieId);
                if(movie.getId() == id)
                    throw new TodoExceptions("No se puede eliminar la Movie porque tiene Reservas asociadas", HttpStatus.BAD_REQUEST);
                else
                    return movie;
            }).collect(Collectors.toList());
            return booking;
        }).collect(Collectors.toList());

        movieRepository.deleteById(id);
    }
}
