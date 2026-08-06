package com.stocksociety.supplier_service.repository;

import com.stocksociety.supplier_service.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByCountry(String country);

    List<Supplier> findByRatingGreaterThanEqual(Double rating);

    List<Supplier> findByCountryAndRatingGreaterThanEqual(String country, Double rating);

}