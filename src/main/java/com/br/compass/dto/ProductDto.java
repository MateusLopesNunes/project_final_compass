package com.br.compass.dto;

import org.springframework.data.domain.Page;

import com.br.compass.model.Product;

public class ProductDto {

	private Long id;
	private String name;
	private String description;
	private Double price;
	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
	}
	
	public ProductDto(Long id, String name, String description, Double price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}



	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}
	
	public static Page<ProductDto> modelToDtoPage(Page<Product> product) {
		return product.map(ProductDto::new);
	}
}
