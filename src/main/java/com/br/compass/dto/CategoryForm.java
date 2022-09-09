package com.br.compass.dto;

import com.br.compass.model.Category;
import com.br.compass.model.Product;
import com.br.compass.repository.CategoryRepository;

public class CategoryForm {

    private String name;

    public String getName() {
        return name;
    }

    public Category dtoToModel() {
        return new Category(name);
    }

    public Category update(Long id, CategoryRepository categoryRepository) {
        Category category = categoryRepository.findById(id).get();
        category.setName(name);
        return categoryRepository.save(category);
    }
}
