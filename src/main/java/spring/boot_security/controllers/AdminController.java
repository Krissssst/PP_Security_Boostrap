package spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.boot_security.models.User;
import spring.boot_security.service.RoleService;
import spring.boot_security.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String show(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.getUserByUsername(email));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String addNewUser(@ModelAttribute("user") User user, @PathVariable("id") int id,
                             @RequestParam(value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
