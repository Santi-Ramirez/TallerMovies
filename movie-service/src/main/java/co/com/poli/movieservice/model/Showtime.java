package co.com.poli.movieservice.model;


import lombok.Data;
import java.util.Date;

@Data
public class Showtime {
    private Long id;
    private Date date;
    private Long[] moviesIds;
}
