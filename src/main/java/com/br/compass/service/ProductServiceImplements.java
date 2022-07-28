package com.br.compass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;
import com.br.compass.exception.ObjectNotFoundException;
import com.br.compass.model.Product;
import com.br.compass.repository.ProductRepository;

@Service
public class ProductServiceImplements implements ProductService {

	private ProductRepository productRepository;

	@Autowired
	public ProductServiceImplements(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Page<ProductDto> findAll(Pageable page) {
		Page<Product> products = productRepository.findAll(page);
		return ProductDto.modelToDtoPage(products);
	}

	@Override
	public ProductDto saveProduct(ProductForm productForm, UriComponentsBuilder builder) {
		Product product = productRepository.save(productForm.dtoToProduct());
		return new ProductDto(product);
	}

	@Override
	public ProductDto findById(Long id) {
		Product product = verifyExists(id);
		return new ProductDto(product);
	}

	@Override
	public ProductDto deleteById(Long id) {
		Product product = verifyExists(id);
		productRepository.deleteById(id);
		return new ProductDto(product);
	}

	@Override
	public ProductDto updateProduct(ProductForm productForm, Long id) {
		verifyExists(id);
		Product update = productForm.update(id, productRepository);
		return new ProductDto(update);
	}

	@Override
	public Page<ProductDto> search(Double maxPrice, Double minPrice, String q, Pageable page) {
		Page<Product> product = productRepository.findByName(maxPrice, minPrice, q, page);
		return ProductDto.modelToDtoPage(product);
	}
	
	private Product verifyExists(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found"));
		return product;
	}
}
