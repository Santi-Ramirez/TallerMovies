package co.com.poli.movieservice.model;

import lombok.Data;

@Data
public class Booking {
    private Long id;
    private Long userId;
    private Long showtimeId;
    private Long[] moviesIds;
}
