package co.com.poli.showtimeservice.service;

import co.com.poli.showtimeservice.clientFeign.MovieClient;
import co.com.poli.showtimeservice.exceptions.TodoExceptions;
import co.com.poli.showtimeservice.model.Movie;
import co.com.poli.showtimeservice.persistence.entity.Showtime;
import co.com.poli.showtimeservice.persistence.repository.ShowtimeRepository;
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
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieClient movieClient;

    @Override
    public List<Showtime> findAllShowTimes() {
        ModelMapper modelMapper = new ModelMapper();
        List<Showtime> showtimes = showtimeRepository.findAll().stream().map(showtime -> {
            Long[] ids = showtime.getMoviesIds();
            List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
            List<Movie> movies = idsMovies.stream().map(movieId -> {
                Movie movie = modelMapper.map(movieClient.findMovieById(movieId).getData(), Movie.class);
                return movie;
            }).collect(Collectors.toList());
            showtime.setMovies(movies);
            return showtime;
        }).collect(Collectors.toList());
        return showtimes;
    }

    @Override
    public Showtime createShowTime(Showtime showtime) {
        Long[] ids = new Long[0];
        List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
        ModelMapper modelMapper = new ModelMapper();
        List<Movie> movies = showtime.getMovies().stream().map( movie -> {
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
            showtime.setMoviesIds(ids);
            showtime.setMovies(movies);
            showtimeRepository.save(showtime);
        return showtime;
    }

    @Override
    public Showtime findShowTimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        if(showtime == null){
            throw new TodoExceptions("El id del Showtime no exíste", HttpStatus.BAD_REQUEST);
        }
        Long[] ids = showtime.getMoviesIds();
        List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
        List<Movie> movies = idsMovies.stream().map(movieId -> {
            Movie movie = modelMapper.map(movieClient.findMovieById(movieId).getData(), Movie.class);
            return movie;
        }).collect(Collectors.toList());
        showtime.setMovies(movies);
        return showtime;
    }

    @Override
    public Showtime updateShowTime(Showtime showtime) {
        Showtime showtimeDB = findShowTimeById(showtime.getId());
        /*if (showtimeDB == null){
            return null;
        }*/
        Long[] ids = new Long[0];
        List<Long> idsMovies = new ArrayList<>(Arrays.asList(ids));
        ModelMapper modelMapper = new ModelMapper();
        List<Movie> movies = showtime.getMovies().stream().map( movie -> {
            Movie movieMap = modelMapper.map(movieClient.findMovieById(movie.getId()).getData(), Movie.class);
            if(movieMap.getId() == null)
                throw new TodoExceptions("Hay Id(s) digitado(s) en Movies que no exíste(n)", HttpStatus.BAD_REQUEST);
            else{
                idsMovies.add(movieMap.getId());
                return movieMap;
            }
        }).collect(Collectors.toList());
        //Convierto la Lista a Array nuevamente
        ids = new Long[idsMovies.size()];
        idsMovies.toArray(ids);
        //Seteamos los datos nuevos en el Showtime
        showtimeDB.setDate(showtime.getDate());
        showtimeDB.setMoviesIds(showtime.getMoviesIds());
        showtimeDB.setMoviesIds(ids);
        showtimeDB.setMovies(movies);
        //Guardamos y retornamos el Showtime actualizado

        return showtimeRepository.save(showtimeDB);
    }
}
