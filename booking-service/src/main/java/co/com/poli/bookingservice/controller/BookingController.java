package co.com.poli.bookingservice.controller;

import co.com.poli.bookingservice.helpers.ErrorMessage;
import co.com.poli.bookingservice.helpers.Response;
import co.com.poli.bookingservice.helpers.ResponseBuild;
import co.com.poli.bookingservice.model.Showtime;
import co.com.poli.bookingservice.model.User;
import co.com.poli.bookingservice.persistence.entity.Booking;
import co.com.poli.bookingservice.service.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final ResponseBuild builder;

    @PostMapping
    public Response createBooking(@Valid @RequestBody Booking booking, BindingResult result){
        if(result.hasErrors()){
            return builder.badRequest(formatMessage(result));
        }
        Booking booking1 = bookingService.createBooking(booking);
        return builder.createSuccess(booking1);
    }

    @GetMapping("/{id}")
    public Response findBookingById(@PathVariable("id") Long id){
        Booking booking = bookingService.findBookingById(id);
        if(booking == null) {
            return builder.notFound();
        }
        return builder.success(booking);
    }

    @GetMapping("/userId/{userId}")
    public List<Booking> findBookingsByUserId(@PathVariable("userId") Long userId){
        return bookingService.findBookingsByUserId(userId);
    }

    @GetMapping
    public List<Booking> findAllBookings() {
        return bookingService.findAllBookings();
    }

    @DeleteMapping("/{id}")
    public Response deleteBooking(@PathVariable("id") Long id){
        Booking booking = bookingService.findBookingById(id);
        if (booking == null){
            return builder.notFound();
        }
        bookingService.deleteBookingById(booking.getId());
        return builder.success(booking);
    }


    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put(error.getField(), error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .error(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
