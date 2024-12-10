package com.example.blog.controller;

import com.example.blog.model.AppRole;
import com.example.blog.model.AppUser;
import com.example.blog.model.UserRole;
import com.example.blog.repository.IAppRoleRepository;
import com.example.blog.repository.IUserRoleRepository;
import com.example.blog.service.user.IAppUserService;
import com.example.blog.ultil.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IAppRoleRepository appRoleRepository;
    @Autowired
    private IUserRoleRepository userRoleRepository;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model, Principal principal) {
        if (principal != null) {
            System.out.println("Username: " + principal.getName());
        } else {
            System.out.println("Not logged in");
        }

        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "Welcome to our website!");
        return "welcomePage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        String userName = principal.getName();
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "userInfoPage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "adminPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("userInfo", userInfo);
            String message = "Hi " + principal.getName() + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }
        return "403Page";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") AppUser user,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      Model model) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        try {
            appUserService.save(user);

            AppRole userRole = appRoleRepository.findByRoleName("ROLE_USER");
            if (userRole == null) {
                throw new Exception("Default user role not found");
            }

            UserRole newUserRole = new UserRole();
            newUserRole.setAppUser(user);
            newUserRole.setAppRole(userRole);
            userRoleRepository.save(newUserRole);

        } catch (Exception e) {
            model.addAttribute("error", "Error registering user: " + e.getMessage());
            return "register";
        }

        return "redirect:/login";
    }
}