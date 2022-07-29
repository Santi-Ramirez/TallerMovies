package co.com.poli.userservice.persistence.repository;

import co.com.poli.userservice.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Busqueda por nombre para la prueba TestJPA
    User findUserByName(String name);
}
