package co.com.poli.userservice;

import co.com.poli.userservice.clientFeign.BookingClient;
import co.com.poli.userservice.persistence.entity.User;
import co.com.poli.userservice.persistence.repository.UserRepository;
import co.com.poli.userservice.service.UserService;
import co.com.poli.userservice.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserServiceMockTest {

    @Mock
    private UserRepository userRepository;
    private BookingClient bookingClient;
    private UserService userService;

    @BeforeEach
    public void begin(){
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, bookingClient);

        User user = User.builder()
                .id(1L)
                .name("Santiago")
                .lastname("Ramirez").build();

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
    }

    @Test
    public void when_findById_return_User(){
        User user = userService.findUserById(1L);
        Assertions.assertThat(user.getName()).isEqualTo("Santiago");
    }
}
