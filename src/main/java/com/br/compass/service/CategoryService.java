package com.br.compass.service;

import com.br.compass.dto.CategoryDto;
import com.br.compass.dto.CategoryForm;
import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

public interface CategoryService {

	public Page<CategoryDto> findAll(Pageable page);

	public CategoryDto saveProduct(CategoryForm categoryForm, UriComponentsBuilder builder);

	public CategoryDto findById(Long id);

	public CategoryDto deleteById(Long id);

	public CategoryDto updateProduct(CategoryForm categoryForm, Long id);

}
