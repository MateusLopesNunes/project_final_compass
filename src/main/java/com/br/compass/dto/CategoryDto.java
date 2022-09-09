package com.br.compass.dto;

import com.br.compass.model.Category;
import org.springframework.data.domain.Page;

public class CategoryDto {

    private Long id;
    private String name;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public static Page<CategoryDto> modelToDtoPage(Page<Category> categories) {
        return categories.map(CategoryDto::new);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
