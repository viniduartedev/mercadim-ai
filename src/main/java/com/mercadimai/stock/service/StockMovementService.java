package com.mercadimai.stock.service;

import com.mercadimai.stock.dto.StockMovementResponse;
import com.mercadimai.stock.entity.StockMovement;
import com.mercadimai.stock.enums.StockMovementType;
import com.mercadimai.stock.mapper.StockMovementMapper;
import com.mercadimai.stock.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final StockMovementMapper stockMovementMapper;

    @Transactional(readOnly = true)
    public Page<StockMovementResponse> list(Long productId, StockMovementType type, Pageable pageable) {
        Page<StockMovement> page;

        if (productId == null && type == null) {
            page = stockMovementRepository.findAll(pageable);
        } else if (productId != null && type != null) {
            page = stockMovementRepository.findByProductIdAndTipo(productId, type, pageable);
        } else if (productId != null) {
            page = stockMovementRepository.findByProductId(productId, pageable);
        } else {
            page = stockMovementRepository.findByTipo(type, pageable);
        }

        return page.map(stockMovementMapper::toResponse);
    }
}
