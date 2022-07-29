package co.com.poli.movieservice;

import co.com.poli.movieservice.persistence.entity.Movie;
import co.com.poli.movieservice.persistence.repository.MovieRepository;
import co.com.poli.movieservice.service.MovieService;
import co.com.poli.movieservice.service.MovieServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MovieServiceMockTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    public void begin(){
        MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(movieRepository);

        Movie movie = Movie.builder()
                .id(1L)
                .title("12 Horas para Sobrevivir")
                .director("James DeMonaco")
                .rating(5).build();

        Mockito.when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));
    }

    @Test
    public void when_findById_return_Movie(){
        Movie movie = movieService.findMovieById(1L);
        Assertions.assertThat(movie.getTitle()).isEqualTo("12 Horas para Sobrevivir");
    }
}
