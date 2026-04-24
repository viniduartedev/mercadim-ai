package com.mercadimai.category.service;

import com.mercadimai.category.dto.CategoryRequest;
import com.mercadimai.category.dto.CategoryResponse;
import com.mercadimai.category.entity.Category;
import com.mercadimai.category.mapper.CategoryMapper;
import com.mercadimai.category.repository.CategoryRepository;
import com.mercadimai.exception.ConflictException;
import com.mercadimai.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public Page<CategoryResponse> list(Boolean active, Pageable pageable) {
        if (active == null) {
            return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);
        }

        return categoryRepository.findByAtivo(active, pageable).map(categoryMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category category = findEntityById(id);
        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        String normalizedName = normalizeName(request.name());
        validateUniqueName(normalizedName, null);

        Category saved = categoryRepository.save(Category.builder()
                .nome(normalizedName)
                .ativo(true)
                .build());

        return categoryMapper.toResponse(saved);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = findEntityById(id);
        String normalizedName = normalizeName(request.name());
        validateUniqueName(normalizedName, id);

        category.setNome(normalizedName);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse updateStatus(Long id, boolean active) {
        Category category = findEntityById(id);
        category.setAtivo(active);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    private Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
    }

    private void validateUniqueName(String name, Long currentId) {
        categoryRepository.findByNomeIgnoreCase(name).ifPresent(existing -> {
            if (currentId == null || !existing.getId().equals(currentId)) {
                throw new ConflictException("Já existe uma categoria com este nome");
            }
        });
    }

    private String normalizeName(String name) {
        return name.trim();
    }
}
