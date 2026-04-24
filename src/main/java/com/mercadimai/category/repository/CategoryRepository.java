package com.mercadimai.category.repository;

import com.mercadimai.category.entity.Category;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNomeIgnoreCase(String nome);

    Page<Category> findByAtivo(boolean ativo, Pageable pageable);
}
