package co.com.poli.userservice.service;

import co.com.poli.userservice.persistence.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserById( Long id );

    User createUser(User user);

    void deleteUserById(Long id);
}
