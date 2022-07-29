package co.com.poli.userservice.service;

import co.com.poli.userservice.clientFeign.BookingClient;
import co.com.poli.userservice.exceptions.TodoExceptions;
import co.com.poli.userservice.model.Booking;
import co.com.poli.userservice.persistence.entity.User;
import co.com.poli.userservice.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BookingClient bookingClient;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        List<Booking> bookings = bookingClient.findBookingsByUserId(id);
        if(!bookings.isEmpty())
            throw new TodoExceptions("No se puede eliminar el User porque tiene reservas asociadas", HttpStatus.BAD_REQUEST);
        else
            userRepository.deleteById(id);
    }
}
