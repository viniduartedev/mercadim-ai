package com.mercadimai.category.controller;

import com.mercadimai.category.dto.CategoryRequest;
import com.mercadimai.category.dto.CategoryResponse;
import com.mercadimai.category.dto.CategoryStatusRequest;
import com.mercadimai.category.service.CategoryService;
import com.mercadimai.shared.api.ApiSuccessResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<CategoryResponse>>> list(
            @RequestParam(required = false) Boolean active
    ) {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Categorias carregadas com sucesso",
                categoryService.list(active)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CategoryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Categoria carregada com sucesso",
                categoryService.getById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiSuccessResponse.of(
                "Categoria criada com sucesso",
                categoryService.create(request)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Categoria atualizada com sucesso",
                categoryService.update(id, request)
        ));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ApiSuccessResponse<CategoryResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody CategoryStatusRequest request
    ) {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Status da categoria atualizado com sucesso",
                categoryService.updateStatus(id, request.active())
        ));
    }
}
