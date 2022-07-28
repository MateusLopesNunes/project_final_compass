package com.br.compass.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;

public interface ProductService {

	public Page<ProductDto> findAll(Pageable page);

	public ProductDto saveProduct(ProductForm productForm, UriComponentsBuilder builder);

	public ProductDto findById(Long id);

	public ProductDto deleteById(Long id);

	public ProductDto updateProduct(ProductForm productForm, Long id);

	public Page<ProductDto> search(Double maxPrice, Double minPrice, String q, Pageable page);
}
