package service;

import java.util.List;
import java.util.Optional;
import model.User;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    Optional<User> get(Long id);
}
