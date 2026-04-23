package com.mercadimai.category.mapper;

import com.mercadimai.category.dto.CategoryResponse;
import com.mercadimai.category.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getNome(),
                category.isAtivo(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
