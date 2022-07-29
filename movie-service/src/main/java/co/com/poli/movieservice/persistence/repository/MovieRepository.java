package co.com.poli.movieservice.persistence.repository;

import co.com.poli.movieservice.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    //Busqueda por titulo para la prueba JpaTest
    Movie findMovieByTitle(String title);
}
