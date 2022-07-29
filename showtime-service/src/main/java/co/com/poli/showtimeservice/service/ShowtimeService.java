package co.com.poli.showtimeservice.service;

import co.com.poli.showtimeservice.model.Movie;
import co.com.poli.showtimeservice.persistence.entity.Showtime;

import java.util.List;

public interface ShowtimeService {

    List<Showtime> findAllShowTimes();

    Showtime createShowTime(Showtime showtime);

    Showtime findShowTimeById(Long id);

    Showtime updateShowTime(Showtime showtime);
}
