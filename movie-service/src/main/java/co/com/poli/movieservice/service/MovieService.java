package co.com.poli.movieservice.service;

import co.com.poli.movieservice.persistence.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAllMovies();

    Movie createMovie(Movie movie);

    Movie findMovieById(Long id);

    void deleteMovieById( Long id );
}
