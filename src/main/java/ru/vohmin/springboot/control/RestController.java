package ru.vohmin.springboot.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vohmin.springboot.model.User;
import ru.vohmin.springboot.repository.RoleRepository;
import ru.vohmin.springboot.service.UserService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/rest")
public class RestController {
    private final UserService service;
    private final RoleRepository roleRepository;

    public RestController(UserService service, RoleRepository roleRepository) {
        this.service = service;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = service.getUsers();
        return users != null && !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long id) {
        final User user = service.findUserById(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping( "/create")
    public ResponseEntity<Void> create(@RequestBody User user, String[] roles) {
        service.saveUser(user, roles);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        service.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/rest/user/")
    public ResponseEntity<Void> delete(@RequestBody User user) {
        boolean result = service.deleteUser(user.getId());
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
