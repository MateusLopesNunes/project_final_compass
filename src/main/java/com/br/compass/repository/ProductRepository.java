package com.br.compass.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.compass.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT * FROM Product WHERE (:q IS NULL OR (UPPER(name) LIKE UPPER(CONCAT('%', :q, '%'))"
			+ "OR UPPER(description) LIKE UPPER(CONCAT('%', :q, '%'))))"
			+ "AND (:min_price IS NULL OR price >= :min_price)"
			+ "AND (:max_price IS NULL OR price <= :max_price)", nativeQuery = true)
	Page<Product> findByName(@Param("max_price") Double maxPrice, @Param("min_price") Double minPrice, @Param("q") String q, Pageable page);
	
}
