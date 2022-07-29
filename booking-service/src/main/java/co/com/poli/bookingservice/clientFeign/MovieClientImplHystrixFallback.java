package co.com.poli.bookingservice.clientFeign;

import co.com.poli.bookingservice.helpers.Response;
import co.com.poli.bookingservice.helpers.ResponseBuild;
import co.com.poli.bookingservice.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieClientImplHystrixFallback implements MovieClient{

    private final ResponseBuild builder;

    @Override
    public Response findAllMovies() {
        return builder.success(new Movie());
    }

    @Override
    public Response findMovieById(Long id) {
        return builder.success(new Movie());
    }
}
