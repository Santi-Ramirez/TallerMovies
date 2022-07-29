package co.com.poli.userservice.model;

import lombok.Data;

@Data
public class Booking {
    private Long id;
    private Long userId;
    private Long showtimeId;
}
