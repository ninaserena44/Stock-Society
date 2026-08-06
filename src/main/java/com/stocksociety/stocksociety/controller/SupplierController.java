package com.stocksociety.stocksociety.controller;

import com.stocksociety.stocksociety.service.SupplierServiceClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupplierController {

    private final SupplierServiceClient supplierServiceClient;

    public SupplierController(SupplierServiceClient supplierServiceClient) {
        this.supplierServiceClient = supplierServiceClient;
    }

    @GetMapping("/suppliers")
    public String suppliers(Model model) {

        model.addAttribute(
                "suppliers",
                supplierServiceClient.getSuppliers()
        );

        return "suppliers/list";
    }
}