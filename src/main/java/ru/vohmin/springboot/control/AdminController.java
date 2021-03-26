package ru.vohmin.springboot.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vohmin.springboot.model.Role;
import ru.vohmin.springboot.model.User;
import ru.vohmin.springboot.repository.RoleRepository;
import ru.vohmin.springboot.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(UserService service, RoleRepository roleRepository) {
        this.service = service;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/users")
    public String getUsers(ModelMap model, Principal principal) {
        model.addAttribute("authUser", getAuthorizedUser(principal));
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("users", service.getUsers());
        return "users";
    }

    @PostMapping("/add_user")
    public String addUser(@ModelAttribute User user, @RequestParam("roles") String[] rolesFromHtml) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roleSet = user.getRoles();
        for (String roleId : rolesFromHtml) {
            roleSet.add(roleRepository.findById(Long.valueOf(roleId)).get());
        }
        service.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/del_user/")
    public String delUser(@ModelAttribute User user) {
        service.deleteUser(user.getId());
        return "redirect:/admin/users";
    }

    @PostMapping("/update/{id}")
    public String redirectToMergePage(@PathVariable @Validated Long id, ModelMap map) {
        map.addAttribute("user", service.findUserById(id));
        return "redirect:/admin/users";
    }

    @GetMapping("/roles")
    public String getRoles() {
        List<Role> all = roleRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("{[");
        for (Role role : all) {
            sb.append(role.getName()).append(" ");
        }
        sb.append("]}");
        return sb.toString();
    }
    @PostMapping("/update/merge")
    public String updateUser(@ModelAttribute User user) {
        service.updateUser(user);
        return "redirect:/admin/users";
    }

    private User getAuthorizedUser(Principal principal) {
        return service.findByUsername(principal.getName());
    }
}
