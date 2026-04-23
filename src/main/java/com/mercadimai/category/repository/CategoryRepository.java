package com.mercadimai.category.repository;

import com.mercadimai.category.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
