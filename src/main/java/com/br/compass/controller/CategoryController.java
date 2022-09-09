package com.br.compass.controller;

import com.br.compass.dto.CategoryDto;
import com.br.compass.dto.CategoryForm;
import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;
import com.br.compass.service.CategoryService;
import com.br.compass.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<Page<CategoryDto>> list(Pageable page) {
		Page<CategoryDto> categories = categoryService.findAll(page);
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
		CategoryDto category = categoryService.findById(id);
		return ResponseEntity.ok(category);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryForm categoryForm, UriComponentsBuilder builder) {
		CategoryDto category = categoryService.saveProduct(categoryForm, builder);
		URI uri = builder.path("/category/{id}").buildAndExpand(category.getId()).toUri();
		return ResponseEntity.created(uri).body(category);
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<CategoryDto> delete(@PathVariable Long id) {
		CategoryDto category = categoryService.deleteById(id);
		return ResponseEntity.ok(category);
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> update(@RequestBody @Valid CategoryForm productForm, @PathVariable Long id) {
		CategoryDto category = categoryService.updateProduct(productForm, id);
		return ResponseEntity.ok(category);
	}

}
