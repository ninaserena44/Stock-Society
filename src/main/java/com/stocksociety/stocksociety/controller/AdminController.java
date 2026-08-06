package com.stocksociety.stocksociety.controller;

import com.stocksociety.stocksociety.repository.ItemRepository;
import com.stocksociety.stocksociety.service.SupplierServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ItemRepository itemRepository;
    private final SupplierServiceClient supplierServiceClient;

    public AdminController(ItemRepository itemRepository,
                           SupplierServiceClient supplierServiceClient) {

        this.itemRepository = itemRepository;
        this.supplierServiceClient = supplierServiceClient;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("items", itemRepository.findAll());

        model.addAttribute("suppliers",
                supplierServiceClient.getSuppliers());

        return "admin/dashboard";
    }

}