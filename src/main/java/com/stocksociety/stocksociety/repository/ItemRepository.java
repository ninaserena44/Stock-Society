package com.stocksociety.stocksociety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stocksociety.stocksociety.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
