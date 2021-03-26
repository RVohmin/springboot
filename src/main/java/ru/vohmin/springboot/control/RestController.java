package ru.vohmin.springboot.control;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vohmin.springboot.model.Role;
import ru.vohmin.springboot.model.User;
import ru.vohmin.springboot.repository.RoleRepository;
import ru.vohmin.springboot.service.UserService;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping(value = "/rest")
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

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getRole() {
        List<Role> roles = roleRepository.findAll();
        return roles != null && !roles.isEmpty() ?
                new ResponseEntity<>(roles, HttpStatus.OK) :
                new ResponseEntity<>(roles, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long id) {
        final User user = service.findUserById(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping( "/create")
    public ResponseEntity<Void> create(User user, @RequestAttribute(name = "roles") String[] roles) {
        service.saveUser(user, roles);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        service.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        boolean result = service.deleteUser(id);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private User getUser(String user, String roleIds) {
        Gson gson = new Gson();
        User userFromJson = gson.fromJson(user, User.class);
        List<String> roleIdsFromJson = gson.fromJson(roleIds, ArrayList.class);
        String[] roleIdsFromList = new String[roleIdsFromJson.size()];
        for (int i=0; i<roleIdsFromList.length; i++) {
            roleIdsFromList[i] = roleIdsFromJson.get(i);
        }
        userFromJson.setRoles(service.getRolesFromArray(roleIdsFromList));
        return userFromJson;
    }
}
