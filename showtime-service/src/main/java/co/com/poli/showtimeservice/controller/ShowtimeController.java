package co.com.poli.showtimeservice.controller;

import co.com.poli.showtimeservice.helpers.ErrorMessage;
import co.com.poli.showtimeservice.helpers.Response;
import co.com.poli.showtimeservice.helpers.ResponseBuild;
import co.com.poli.showtimeservice.model.Movie;
import co.com.poli.showtimeservice.persistence.entity.Showtime;
import co.com.poli.showtimeservice.service.ShowtimeService;
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
@RequestMapping("/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;
    private final ResponseBuild builder;

    @PostMapping
    public Response createShowTime(@Valid @RequestBody Showtime showtime, BindingResult result){
        if (result.hasErrors()){
            return builder.badRequest(formatMessage(result));
        }
        Showtime showtimeCreate = showtimeService.createShowTime(showtime);
        return builder.createSuccess(showtimeCreate);
    }

    @GetMapping("/{id}")
    public Response findShowTimeById(@PathVariable("id") Long id) {
        Showtime showtime = showtimeService.findShowTimeById(id);
        if(showtime == null) {
            return builder.notFound();
        }
        return builder.success(showtime);
    }

    @GetMapping
    public List<Showtime> findAllShowTimes(){
        return showtimeService.findAllShowTimes();
    }

    @PutMapping("/{id}")
    public Response updateShowTime(@PathVariable("id") Long id,@RequestBody Showtime showtime){
        //Showtime showtimeUpdate = showtimeService.findShowTimeById(id);
        showtime.setId(id);
        Showtime showtimeFind = showtimeService.findShowTimeById(id);
        if(showtimeFind == null){
            return builder.notFound();
        }
        Showtime showtimeUpdate = showtimeService.updateShowTime(showtime);
        return builder.success(showtimeUpdate);
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
