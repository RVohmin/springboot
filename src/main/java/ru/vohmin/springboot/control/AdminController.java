package ru.vohmin.springboot.control;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vohmin.springboot.model.User;
import ru.vohmin.springboot.repository.RoleRepository;
import ru.vohmin.springboot.service.UserService;

import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;
    private final RoleRepository roleRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(UserService service, RoleRepository roleRepository) {
        this.service = service;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/users")
    public String getUsers(ModelMap model, Principal principal) {
        model.addAttribute("authUser", getAuthorizedUser(principal));
        model.addAttribute("users", service.getUsers());
        return "users";
    }

    @GetMapping("/add_page")
    public String redirectToAddUserForm(ModelMap map, Principal principal) {
        map.addAttribute("authUser", getAuthorizedUser(principal));
        map.addAttribute("user", new User());
        map.addAttribute("allRoles", roleRepository.findAll());
        return "add_user";
    }

    @PostMapping("/add_user")
    public String addUser(@ModelAttribute User user, @RequestParam("roles") String[] rolesFromHtml) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.saveUser(user, rolesFromHtml);
        return "redirect:/admin/users";
    }

    @PostMapping("/del_user/{id}")
    public String delUser(@PathVariable @Validated @NotNull Long id) {
        service.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/update/{id}")
    public String redirectToMergePage(@PathVariable @Validated @NotNull Long id, ModelMap map) {
        map.addAttribute("user", service.findUserById(id));
        map.addAttribute("allRoles", roleRepository.findAll());
        return "update";
    }

    @PostMapping("/update/merge")
    public String updateUser(@ModelAttribute User user) {
        service.updateUser(user);
        return "redirect:/admin/users";
    }

    private User getAuthorizedUser(Principal principal) {
//        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.findByUsername(principal.getName());
    }
}
