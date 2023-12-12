package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showIndexPage() {
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String showHomePage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "all-users";
    }

    @RequestMapping("/admin/addUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "add-user";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        userService.addRoleToUser(user.getUsername(), role);
        return "redirect:/admin";
    }

    @PostMapping(value = "/saveAfterEditUser")
    public String saveAfterEditUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/updateInfo")
    public String updateUser(@RequestParam("userId") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("roleListToUser", new ArrayList<Role>());
        return "edit-user";
    }

    @RequestMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}