package org.example.library.controller;

import org.example.library.model.User;
import org.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") User user,
                               HttpServletResponse response,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        User authenticatedUser = userService.authenticate(user.getUserName(), user.getPassword());
        if (authenticatedUser != null) {
            session.setAttribute("loggedInUser", authenticatedUser);

            if (user.isRememberMe()) {
                Cookie cookie = new Cookie("username", authenticatedUser.getUserName());
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                response.addCookie(cookie);
            }

            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (userService.isUsernameTaken(user.getUserName())) {
            redirectAttributes.addFlashAttribute("error", "Username is already taken");
            return "redirect:/user/register";
        }

        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("success", "Registration successful. Please login.");
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("loggedInUser");
        Cookie cookie = new Cookie("username", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}