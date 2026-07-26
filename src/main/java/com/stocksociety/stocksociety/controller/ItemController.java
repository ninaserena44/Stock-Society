//author PB

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

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showItems(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Brand brand,
        @RequestParam(required = false) Category category,
        @RequestParam(required = false) Supplier supplier,
        @RequestParam(required = false) String stockStatus,
        @RequestParam(defaultValue = "itemName") String sortBy,
        @RequestParam(defaultValue = "asc") String direction,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model
    ) {
        String normalizedKeyword =
            StringUtils.hasText(keyword) ? keyword.trim() : null;

        String normalizedStockStatus =
            StringUtils.hasText(stockStatus) ? stockStatus : null;

        Set<String> allowedSortFields = Set.of(
            "itemName",
            "price",
            "quantityAvailable",
            "createdAt"
        );

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "itemName";
        }

        if (page < 0) {
            page = 0;
        }

        if (size != 5 && size != 10 && size != 20) {
            size = 5;
        }

        Sort.Direction sortDirection =
            direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(sortDirection, sortBy)
        );

        Page<Item> itemPage = itemRepository.searchItems(
            normalizedKeyword,
            brand,
            category,
            supplier,
            normalizedStockStatus,
            pageable
        );

        model.addAttribute("items", itemPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedSupplier", supplier);
        model.addAttribute("stockStatus", stockStatus);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalResults", itemPage.getTotalElements());

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