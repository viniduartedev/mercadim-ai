package com.mercadimai.stock.mapper;

import com.mercadimai.stock.dto.StockMovementResponse;
import com.mercadimai.stock.entity.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovementResponse toResponse(StockMovement movement) {
        return new StockMovementResponse(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getProduct().getNome(),
                movement.getTipo(),
                movement.getQuantity(),
                movement.getReason(),
                movement.getReferenceCode(),
                movement.getCreatedAt()
        );
    }
}
