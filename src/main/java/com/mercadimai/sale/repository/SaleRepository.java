package com.mercadimai.sale.repository;

import com.mercadimai.sale.entity.Sale;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByNumeroVenda(String numeroVenda);
}
