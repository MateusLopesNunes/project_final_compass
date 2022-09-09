package com.br.compass.service;

import com.br.compass.dto.CategoryDto;
import com.br.compass.dto.CategoryForm;
import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;
import com.br.compass.exception.ObjectNotFoundException;
import com.br.compass.model.Category;
import com.br.compass.model.Product;
import com.br.compass.repository.CategoryRepository;
import com.br.compass.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CategoryServiceImplements implements CategoryService {

	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImplements(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Page<CategoryDto> findAll(Pageable page) {
		Page<Category> categories = categoryRepository.findAll(page);
		return CategoryDto.modelToDtoPage(categories);
	}

	@Override
	public CategoryDto saveProduct(CategoryForm categoryForm, UriComponentsBuilder builder) {
		Category category = categoryRepository.save(categoryForm.dtoToModel());
		return new CategoryDto(category);
	}

	@Override
	public CategoryDto findById(Long id) {
		Category category = verifyExists(id);
		return new CategoryDto(category);
	}

	@Override
	public CategoryDto deleteById(Long id) {
		Category category = verifyExists(id);
		categoryRepository.deleteById(id);
		return new CategoryDto(category);
	}

	@Override
	public CategoryDto updateProduct(CategoryForm categoryForm, Long id) {
		verifyExists(id);
		Category update = categoryForm.update(id, categoryRepository);
		return new CategoryDto(update);
	}
	
	private Category verifyExists(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found"));
		return category;
	}
}
