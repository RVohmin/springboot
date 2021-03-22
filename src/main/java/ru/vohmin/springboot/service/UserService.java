package ru.vohmin.springboot.service;



import org.springframework.security.core.userdetails.UserDetailsService;
import ru.vohmin.springboot.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean saveUser(User user, String[] rolesFromHtml);
    boolean deleteUser(Long id);
    User findUserById(Long id);
    void updateUser(User user);
    List<User> getUsers();
    User findByUsername(String username);
}
