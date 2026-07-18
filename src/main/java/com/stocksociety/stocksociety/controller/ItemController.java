package com.stocksociety.stocksociety.controller;

import com.stocksociety.stocksociety.model.Brand;
import com.stocksociety.stocksociety.model.Category;
import com.stocksociety.stocksociety.model.ClothingSize;
import com.stocksociety.stocksociety.model.Item;
import com.stocksociety.stocksociety.model.Supplier;
import com.stocksociety.stocksociety.repository.ItemRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
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
        model.addAttribute("pageTitle", "Add New Item");
        model.addAttribute("formHeading", "Create Item Record");
        model.addAttribute("submitLabel", "Save Item");

        return "items/form";
    }

    @PostMapping
    public String createItem(
        @Valid @ModelAttribute("item") Item item,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Item");
            model.addAttribute("formHeading", "Create Item Record");
            model.addAttribute("submitLabel", "Save Item");

            return "items/form";
        }

        itemRepository.save(item);

        redirectAttributes.addFlashAttribute(
            "successMessage",
            "Item added successfully."
        );

        return "redirect:/items";
    }

    @GetMapping("/{id}")
    public String showItemDetails(
        @PathVariable Long id,
        Model model
    ) {
        Item item = findItemOrThrow(id);

        model.addAttribute("item", item);

        return "items/details";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(
        @PathVariable Long id,
        Model model
    ) {
        Item item = findItemOrThrow(id);

        model.addAttribute("item", item);
        model.addAttribute("pageTitle", "Edit Item");
        model.addAttribute("formHeading", "Update Item Record");
        model.addAttribute("submitLabel", "Update Item");

        return "items/form";
    }

    @PostMapping("/{id}")
    public String updateItem(
        @PathVariable Long id,
        @Valid @ModelAttribute("item") Item formItem,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            formItem.setItemId(id);

            model.addAttribute("pageTitle", "Edit Item");
            model.addAttribute("formHeading", "Update Item Record");
            model.addAttribute("submitLabel", "Update Item");

            return "items/form";
        }

        Item existingItem = findItemOrThrow(id);

        copyEditableFields(formItem, existingItem);

        itemRepository.save(existingItem);

        redirectAttributes.addFlashAttribute(
            "successMessage",
            "Item updated successfully."
        );

        return "redirect:/items/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteItem(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes
    ) {
        Item item = findItemOrThrow(id);

        itemRepository.delete(item);

        redirectAttributes.addFlashAttribute(
            "successMessage",
            "Item deleted successfully."
        );

        return "redirect:/items";
    }

    private Item findItemOrThrow(Long id) {
        return itemRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Item not found."
            ));
    }

    private void copyEditableFields(
        Item source,
        Item destination
    ) {
        destination.setItemName(source.getItemName());
        destination.setDescription(source.getDescription());
        destination.setSize(source.getSize());
        destination.setColor(source.getColor());
        destination.setPrice(source.getPrice());
        destination.setCategory(source.getCategory());
        destination.setBrand(source.getBrand());
        destination.setSupplier(source.getSupplier());
        destination.setQuantityAvailable(source.getQuantityAvailable());
        destination.setReorderLevel(source.getReorderLevel());
    }
}