package co.com.poli.movieservice;

import co.com.poli.movieservice.persistence.entity.Movie;
import co.com.poli.movieservice.persistence.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MovieRepositoryMockTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void whenFindByTitle_thenReturnDirector(){
        Movie movie = Movie.builder()
                .id(1L)
                .title("12 Horas para Sobrevivir")
                .director("James DeMonaco")
                .rating(5).build();
        movieRepository.save(movie);

        Movie movieDBTest = movieRepository.findMovieByTitle(movie.getTitle());
        Assertions.assertThat(movieDBTest.getDirector()).isEqualTo("James DeMonaco");
    }

}
