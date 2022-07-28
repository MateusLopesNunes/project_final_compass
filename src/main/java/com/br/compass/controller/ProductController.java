package com.br.compass.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;
import com.br.compass.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductDto>> list(Pageable page) {
		Page<ProductDto> products = productService.findAll(page);
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
		ProductDto product = productService.findById(id);
		return ResponseEntity.ok(product);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductForm productForm, UriComponentsBuilder builder) {
		ProductDto product = productService.saveProduct(productForm, builder);
		URI uri = builder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).body(product);
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductDto> delete(@PathVariable Long id) {
		ProductDto product = productService.deleteById(id);
		return ResponseEntity.ok(product);
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> update(@RequestBody @Valid ProductForm productForm, @PathVariable Long id) {
		ProductDto product = productService.updateProduct(productForm, id);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<ProductDto>> search(@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) String q, Pageable page) {
		Page<ProductDto> product = productService.search(maxPrice, minPrice, q, page);
		return ResponseEntity.ok(product);
	}
}
