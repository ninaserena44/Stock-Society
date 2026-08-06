package com.stocksociety.supplier_service.controller;

import com.stocksociety.supplier_service.model.Supplier;
import com.stocksociety.supplier_service.repository.SupplierRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierRepository repository;

    public SupplierController(SupplierRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return repository.save(supplier);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable Long id,
                                   @RequestBody Supplier supplier) {

        supplier.setId(id);

        return repository.save(supplier);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Supplier> searchSuppliers(
            @RequestParam String country,
            @RequestParam Double rating) {

        return repository.findByCountryAndRatingGreaterThanEqual(country, rating);
    }

}