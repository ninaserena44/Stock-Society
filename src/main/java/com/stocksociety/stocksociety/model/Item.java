package com.stocksociety.stocksociety.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @NotBlank(message = "Item name is required.")
    @Size(
        min = 2,
        max = 100,
        message = "Item name must be between 2 and 100 characters."
    )
    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @NotBlank(message = "Description is required.")
    @Size(
        min = 10,
        max = 500,
        message = "Description must be between 10 and 500 characters."
    )
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @NotNull(message = "Please select a size.")
    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false, length = 20)
    private ClothingSize size;

    @NotBlank(message = "Colour is required.")
    @Size(
        min = 2,
        max = 50,
        message = "Colour must be between 2 and 50 characters."
    )
    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @NotNull(message = "Price is required.")
    @DecimalMin(
        value = "0.01",
        message = "Price must be at least $0.01."
    )
    @DecimalMax(
        value = "10000.00",
        message = "Price cannot exceed $10,000."
    )
    @Column(
        name = "price",
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal price;

    @NotNull(message = "Please select a category.")
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 30)
    private Category category;

    @NotNull(message = "Please select a brand.")
    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false, length = 40)
    private Brand brand;

    @NotNull(message = "Please select a supplier.")
    @Enumerated(EnumType.STRING)
    @Column(name = "supplier", nullable = false, length = 50)
    private Supplier supplier;

    @NotNull(message = "Quantity is required.")
    @Min(
        value = 0,
        message = "Quantity cannot be negative."
    )
    @Max(
        value = 100000,
        message = "Quantity cannot exceed 100,000 units."
    )
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;

    @NotNull(message = "Reorder level is required.")
    @Min(
        value = 0,
        message = "Reorder level cannot be negative."
    )
    @Max(
        value = 10000,
        message = "Reorder level cannot exceed 10,000 units."
    )
    @Column(name = "reorder_level", nullable = false)
    private Integer reorderLevel;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Item() {
    }

    public Item(
        String itemName,
        String description,
        ClothingSize size,
        String color,
        BigDecimal price,
        Category category,
        Brand brand,
        Supplier supplier,
        Integer quantityAvailable,
        Integer reorderLevel
    ) {
        this.itemName = itemName;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.supplier = supplier;
        this.quantityAvailable = quantityAvailable;
        this.reorderLevel = reorderLevel;
    }

    @PrePersist
    public void assignCreatedAt() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClothingSize getSize() {
        return size;
    }

    public void setSize(ClothingSize size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
