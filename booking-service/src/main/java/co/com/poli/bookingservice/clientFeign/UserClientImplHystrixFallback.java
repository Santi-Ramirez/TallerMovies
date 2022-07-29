package co.com.poli.bookingservice.clientFeign;

import co.com.poli.bookingservice.helpers.Response;
import co.com.poli.bookingservice.helpers.ResponseBuild;
import co.com.poli.bookingservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientImplHystrixFallback implements UserClient {

    private final ResponseBuild builder;

    @Override
    public Response findAllUsers() {
        return builder.success(new User());
    }

    @Override
    public Response findUserById(Long id) {
        return builder.success(new User());
    }
}
