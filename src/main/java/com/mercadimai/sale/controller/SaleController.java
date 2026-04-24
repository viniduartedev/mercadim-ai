package com.mercadimai.sale.controller;

import com.mercadimai.sale.dto.SaleResponse;
import com.mercadimai.sale.enums.SaleStatus;
import com.mercadimai.sale.service.SaleService;
import com.mercadimai.shared.api.ApiSuccessResponse;
import com.mercadimai.shared.api.PageResponse;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<SaleResponse>>> list(
            @RequestParam(required = false) SaleStatus status,
            @RequestParam(required = false) Long userProfileId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime soldFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime soldTo,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<SaleResponse> page = saleService.list(status, userProfileId, soldFrom, soldTo, pageable);
        return ResponseEntity.ok(ApiSuccessResponse.of("Vendas carregadas com sucesso", PageResponse.from(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<SaleResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiSuccessResponse.of("Venda carregada com sucesso", saleService.getById(id)));
    }
}
