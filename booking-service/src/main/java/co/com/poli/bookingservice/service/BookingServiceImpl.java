package co.com.poli.bookingservice.service;

import co.com.poli.bookingservice.clientFeign.MovieClient;
import co.com.poli.bookingservice.clientFeign.ShowtimeClient;
import co.com.poli.bookingservice.clientFeign.UserClient;
import co.com.poli.bookingservice.exceptions.TodoExceptions;
import co.com.poli.bookingservice.model.Movie;
import co.com.poli.bookingservice.model.Showtime;
import co.com.poli.bookingservice.model.User;
import co.com.poli.bookingservice.persistence.entity.Booking;
import co.com.poli.bookingservice.persistence.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserClient userClient;
    private final ShowtimeClient showtimeClient;

    private final MovieClient movieClient;

    @Override
    public List<Booking> findAllBookings() {
        ModelMapper modelMapper = new ModelMapper();
        List<Booking> bookings = bookingRepository.findAll().stream().map(booking -> {
            User user = modelMapper.map(userClient.findUserById(booking.getUserId()).getData(), User.class);
            Long[] ids = booking.getMoviesIds();
            List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
            List<Movie> movies = idsMovies.stream().map(movieId -> {
                Movie movie = modelMapper.map(movieClient.findMovieById(movieId).getData(), Movie.class);
                return movie;
            }).collect(Collectors.toList());
            booking.setUser(user);
            booking.setMovies(movies);
            return booking;
        }).collect(Collectors.toList());
        return bookings;
    }

    @Override
    public Booking createBooking(Booking booking) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userClient.findUserById(booking.getUserId()).getData(), User.class);
        Showtime showtime = modelMapper.map(showtimeClient.findShowTimeById(booking.getShowtimeId()).getData(), Showtime.class);
        if(user.getId() == null)
            throw new TodoExceptions("El idUser digitado no exíste", HttpStatus.BAD_REQUEST);
        else if (showtime.getId() == null)
            throw new TodoExceptions("El ShowtimeId digitado no exíste", HttpStatus.BAD_REQUEST);
        //Validamos Movies ingresadas y guardamos en el array moviesIds
        Long[] ids = new Long[0];
        List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
        List<Movie> movies = booking.getMovies().stream().map(movie -> {
            Movie movieMap = modelMapper.map(movieClient.findMovieById(movie.getId()).getData(), Movie.class);
            if(movieMap.getId() == null)
                throw new TodoExceptions("Hay Id(s) digitado(s) en Movies que no exíste(n)", HttpStatus.BAD_REQUEST);
            else{
                idsMovies.add(movieMap.getId());
                return movieMap;
            }
        }).collect(Collectors.toList());

        ids = new Long[idsMovies.size()];
        idsMovies.toArray(ids);

        booking.setUser(user);
        booking.setMoviesIds(ids);
        booking.setMovies(movies);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking findBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            throw new TodoExceptions("El id del Booking no exíste", HttpStatus.BAD_REQUEST);
        }
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userClient.findUserById(booking.getUserId()).getData(), User.class);
        Long[] ids = booking.getMoviesIds();
        List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
        List<Movie> movies = idsMovies.stream().map(movieId -> {
            Movie movie = modelMapper.map(movieClient.findMovieById(movieId).getData(), Movie.class);
            return movie;
        }).collect(Collectors.toList());

        booking.setUser(user);
        booking.setMovies(movies);

        return booking;
    }

    @Override
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> findBookingsByUserId(Long userId) {
        //return bookingRepository.findAllByUserId(userId);

        ModelMapper modelMapper = new ModelMapper();
        List<Booking> bookings = bookingRepository.findAllByUserId(userId).stream().map(booking -> {
            User user = modelMapper.map(userClient.findUserById(booking.getUserId()).getData(), User.class);
            Long[] ids = booking.getMoviesIds();
            List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
            List<Movie> movies = idsMovies.stream().map(movieId -> {
                Movie movie = modelMapper.map(movieClient.findMovieById(movieId).getData(), Movie.class);
                return movie;
            }).collect(Collectors.toList());
            booking.setUser(user);
            booking.setMovies(movies);
            return booking;
        }).collect(Collectors.toList());
        return bookings;
    }
}
