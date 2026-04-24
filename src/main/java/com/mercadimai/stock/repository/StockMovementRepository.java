package com.mercadimai.stock.repository;

import com.mercadimai.stock.entity.StockMovement;
import com.mercadimai.stock.enums.StockMovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    @Override
    @EntityGraph(attributePaths = "product")
    Page<StockMovement> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "product")
    Page<StockMovement> findByProductId(Long productId, Pageable pageable);

    @EntityGraph(attributePaths = "product")
    Page<StockMovement> findByProductIdAndTipo(Long productId, StockMovementType tipo, Pageable pageable);

    @EntityGraph(attributePaths = "product")
    Page<StockMovement> findByTipo(StockMovementType tipo, Pageable pageable);
}
