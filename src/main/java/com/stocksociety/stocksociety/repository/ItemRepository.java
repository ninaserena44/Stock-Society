// @author PB

package com.stocksociety.stocksociety.repository;

import com.stocksociety.stocksociety.model.Brand;
import com.stocksociety.stocksociety.model.Category;
import com.stocksociety.stocksociety.model.Item;
import com.stocksociety.stocksociety.model.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
        SELECT i
        FROM Item i
        WHERE (
            :keyword IS NULL
            OR LOWER(i.itemName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(i.color) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        AND (
            :brand IS NULL
            OR i.brand = :brand
        )
        AND (
            :category IS NULL
            OR i.category = :category
        )
        AND (
            :supplier IS NULL
            OR i.supplier = :supplier
        )
        AND (
            :stockStatus IS NULL
            OR (
                :stockStatus = 'AVAILABLE'
                AND i.quantityAvailable > i.reorderLevel
            )
            OR (
                :stockStatus = 'LOW'
                AND i.quantityAvailable > 0
                AND i.quantityAvailable <= i.reorderLevel
            )
            OR (
                :stockStatus = 'OUT'
                AND i.quantityAvailable = 0
            )
        )
        """)
    Page<Item> searchItems(
        @Param("keyword") String keyword,
        @Param("brand") Brand brand,
        @Param("category") Category category,
        @Param("supplier") Supplier supplier,
        @Param("stockStatus") String stockStatus,
        Pageable pageable
    );
}