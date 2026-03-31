package com.mercadimai.sale.repository;

import com.mercadimai.sale.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
