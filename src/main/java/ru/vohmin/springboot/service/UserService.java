package ru.vohmin.springboot.service;



import org.springframework.security.core.userdetails.UserDetailsService;
import ru.vohmin.springboot.model.Role;
import ru.vohmin.springboot.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    boolean saveUser(User user);
    boolean deleteUser(Long id);
    User findUserById(Long id);
    void updateUser(User user);
    List<User> getUsers();
    User findByUsername(String username);

    Set<Role> getRolesFromArray(String[] array);

}
