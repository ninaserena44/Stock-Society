package com.stocksociety.stocksociety.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalItems", 0);
        model.addAttribute("totalStock", 0);
        model.addAttribute("totalOrders", 0);

        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
