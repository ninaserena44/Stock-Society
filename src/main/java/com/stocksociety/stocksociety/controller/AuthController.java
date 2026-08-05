package com.stocksociety.stocksociety.controller;

import com.stocksociety.stocksociety.model.RegistrationForm;
import com.stocksociety.stocksociety.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute(
            "registrationForm",
            new RegistrationForm()
        );

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (!registrationForm.getPassword()
                .equals(registrationForm.getConfirmPassword())) {

            bindingResult.rejectValue(
                "confirmPassword",
                "password.mismatch",
                "The passwords do not match."
            );
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(registrationForm);
        } catch (IllegalArgumentException exception) {
            model.addAttribute(
                "registrationError",
                exception.getMessage()
            );

            return "register";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
}