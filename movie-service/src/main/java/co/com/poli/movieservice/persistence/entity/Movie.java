package co.com.poli.movieservice.persistence.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;
    @NotBlank(message = "El campo title no debe estar vacío")
    @Column(name = "title")
    private String title;
    @NotBlank(message = "El campo director no debe estar vacío")
    @Column(name = "director")
    private String director;
    @Min(value = 1, message = "El campo rating no debe ser menor a 1")
    @Max(value = 5, message = "El campo rating no debe ser mayor a 5")
    @Column(name = "rating")
    private Integer rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
