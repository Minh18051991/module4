package org.example.form_validate.controller;

import jakarta.validation.Valid;
import org.example.form_validate.dto.UserDto;
import org.example.form_validate.model.User;
import org.example.form_validate.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "index";
    }

    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        userService.saveUser(user);
        model.addAttribute("user", user);
        return "result";
    }
}