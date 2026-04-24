package com.mercadimai.stock.controller;

import com.mercadimai.shared.api.ApiSuccessResponse;
import com.mercadimai.shared.api.PageResponse;
import com.mercadimai.stock.dto.StockMovementResponse;
import com.mercadimai.stock.enums.StockMovementType;
import com.mercadimai.stock.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock/movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<StockMovementResponse>>> list(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) StockMovementType type,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<StockMovementResponse> page = stockMovementService.list(productId, type, pageable);
        return ResponseEntity.ok(ApiSuccessResponse.of("Movimentações de estoque carregadas com sucesso", PageResponse.from(page)));
    }
}
