package com.mercadimai.product.controller;

import com.mercadimai.product.dto.ProductRequest;
import com.mercadimai.product.dto.ProductResponse;
import com.mercadimai.product.dto.ProductStatusRequest;
import com.mercadimai.product.service.ProductService;
import com.mercadimai.shared.api.ApiSuccessResponse;
import com.mercadimai.shared.api.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<ProductResponse>>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<ProductResponse> page = productService.list(name, sku, categoryId, active, pageable);

        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Produtos carregados com sucesso",
                PageResponse.from(page)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ProductResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Produto carregado com sucesso",
                productService.getById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiSuccessResponse.of(
                "Produto criado com sucesso",
                productService.create(request)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    ) {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Produto atualizado com sucesso",
                productService.update(id, request)
        ));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ApiSuccessResponse<ProductResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProductStatusRequest request
    ) {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Status do produto atualizado com sucesso",
                productService.updateStatus(id, request.active())
        ));
    }
}
