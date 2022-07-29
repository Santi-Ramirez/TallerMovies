package co.com.poli.userservice;

import co.com.poli.userservice.persistence.entity.User;
import co.com.poli.userservice.persistence.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryMockTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnLastName(){
        User user = User.builder()
                .id(1L)
                .name("Santiago")
                .lastname("Ramirez").build();
        userRepository.save(user);

        User userDBTest = userRepository.findUserByName(user.getName());
        Assertions.assertThat(userDBTest.getLastname()).isEqualTo("Ramirez");
    }


}
