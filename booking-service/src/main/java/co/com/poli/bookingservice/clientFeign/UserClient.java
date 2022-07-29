package co.com.poli.bookingservice.clientFeign;

import co.com.poli.bookingservice.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallback = UserClientImplHystrixFallback.class)
public interface UserClient {

    @GetMapping("/taller-movies/v1/users")
    Response findAllUsers();

    @GetMapping("/taller-movies/v1/users/{id}")
    Response findUserById(@PathVariable("id") Long id);

}
