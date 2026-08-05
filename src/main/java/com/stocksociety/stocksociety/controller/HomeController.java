package com.stocksociety.stocksociety.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.stocksociety.stocksociety.repository.ItemRepository;

@Controller
public class HomeController {

    private final ItemRepository itemRepository;

    public HomeController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    public String home(Model model) {

        long totalItems = itemRepository.count();

        int totalStock = itemRepository
            .findAll()
            .stream()
            .mapToInt(item -> item.getQuantityAvailable())
            .sum();

        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalStock", totalStock);

        // Orders planned for a future deliverable.
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
}