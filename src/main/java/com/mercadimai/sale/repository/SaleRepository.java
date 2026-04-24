package com.mercadimai.sale.repository;

import com.mercadimai.sale.entity.Sale;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

    @Override
    @EntityGraph(attributePaths = {"userProfile"})
    Page<Sale> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"userProfile"})
    Page<Sale> findAll(Specification<Sale> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"items", "items.product", "userProfile"})
    Optional<Sale> findById(Long id);
}
