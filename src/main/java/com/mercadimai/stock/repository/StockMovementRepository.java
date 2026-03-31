package com.mercadimai.stock.repository;

import com.mercadimai.stock.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    boolean existsByObservacao(String observacao);
}
