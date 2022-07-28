package com.br.compass.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.br.compass.model.Product;
import com.br.compass.repository.ProductRepository;

public class ProductForm {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	@Positive
	private Double price;
	
	public ProductForm(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Product dtoToProduct() {
		return new Product(name, description, price);
	}
	public Product update(Long id, ProductRepository repository) {
		Product product = repository.findById(id).get();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		return repository.save(product);
	}
}
