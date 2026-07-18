package com.stocksociety.stocksociety.controller;

import com.stocksociety.stocksociety.model.Brand;
import com.stocksociety.stocksociety.model.Category;
import com.stocksociety.stocksociety.model.ClothingSize;
import com.stocksociety.stocksociety.model.Item;
import com.stocksociety.stocksociety.model.Supplier;
import com.stocksociety.stocksociety.repository.ItemRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @ModelAttribute("brands")
    public Brand[] brands() {
        return Brand.values();
    }

    @ModelAttribute("categories")
    public Category[] categories() {
        return Category.values();
    }

    @ModelAttribute("sizes")
    public ClothingSize[] sizes() {
        return ClothingSize.values();
    }

    @ModelAttribute("suppliers")
    public Supplier[] suppliers() {
        return Supplier.values();
    }

    @GetMapping
    public String showItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());

        return "items/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Item());

        return "items/form";
    }

    @PostMapping
    public String createItem(
        @Valid @ModelAttribute("item") Item item,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "items/form";
        }

        itemRepository.save(item);

        redirectAttributes.addFlashAttribute(
            "successMessage",
            "Item added successfully."
        );

        return "redirect:/items";
    }
}