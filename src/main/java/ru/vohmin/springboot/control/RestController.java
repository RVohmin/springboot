package ru.vohmin.springboot.control;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.vohmin.springboot.model.Role;
import ru.vohmin.springboot.model.User;
import ru.vohmin.springboot.repository.RoleRepository;
import ru.vohmin.springboot.service.UserService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping(value = "/rest")
public class RestController {
    private final PasswordEncoder passwordEncoder;
    private final UserService service;
    private final RoleRepository roleRepository;

    public RestController(PasswordEncoder passwordEncoder, UserService service, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.service = service;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = service.getUsers();
        return users != null && !users.isEmpty() ? ResponseEntity.ok().body(users) : ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getRole() {
        List<Role> roles = roleRepository.findAll();
        return !roles.isEmpty() ? ResponseEntity.ok(roles) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long id) {
        final User user = service.findUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping( "/create")
    public ResponseEntity<Void> create(User user, @RequestAttribute(name = "roles") String[] roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.saveUser(user, roles);
        return ResponseEntity.ok().build();
    }

    @PutMapping (value = "/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        boolean result = service.deleteUser(id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
