package co.com.poli.bookingservice.persistence.entity;

import co.com.poli.bookingservice.model.Movie;
import co.com.poli.bookingservice.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "El campo userId no puede ser nulo")
    @Column(name = "user_id")
    private Long userId;

    @Transient
    private User user;

    @NotNull(message = "El campo showtimeId no puede ser nulo")
    @Column(name = "showtime_id")
    private Long showtimeId;

    private Long[] moviesIds;

    @Transient
    private List<Movie> movies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
