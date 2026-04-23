package com.mercadimai.product.service;

import com.mercadimai.category.entity.Category;
import com.mercadimai.category.repository.CategoryRepository;
import com.mercadimai.exception.ConflictException;
import com.mercadimai.exception.ResourceNotFoundException;
import com.mercadimai.product.dto.ProductRequest;
import com.mercadimai.product.dto.ProductResponse;
import com.mercadimai.product.entity.Product;
import com.mercadimai.product.mapper.ProductMapper;
import com.mercadimai.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ProductResponse> list(
            String name,
            String sku,
            Long categoryId,
            Boolean active,
            Pageable pageable
    ) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if (StringUtils.hasText(name)) {
            String normalizedName = name.trim().toLowerCase();
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + normalizedName + "%"));
        }

        if (StringUtils.hasText(sku)) {
            String normalizedSku = sku.trim().toLowerCase();
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("sku")), normalizedSku));
        }

        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("categoria").get("id"), categoryId));
        }

        if (active != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("ativo"), active));
        }

        return productRepository.findAll(spec, pageable).map(productMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = findEntityById(id);
        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        String normalizedSku = normalizeSku(request.sku());
        validateUniqueSku(normalizedSku, null);

        Category category = findCategoryById(request.categoryId());

        Product saved = productRepository.save(Product.builder()
                .nome(normalizeName(request.name()))
                .sku(normalizedSku)
                .codigoBarras(normalizeNullable(request.barcode()))
                .descricao(normalizeNullable(request.description()))
                .precoVenda(request.salePrice())
                .custo(request.cost())
                .estoqueAtual(request.currentStock())
                .estoqueMinimo(request.minimumStock())
                .ativo(request.active() != null ? request.active() : true)
                .categoria(category)
                .build());

        return productMapper.toResponse(saved);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findEntityById(id);
        String normalizedSku = normalizeSku(request.sku());
        validateUniqueSku(normalizedSku, id);

        Category category = findCategoryById(request.categoryId());

        product.setNome(normalizeName(request.name()));
        product.setSku(normalizedSku);
        product.setCodigoBarras(normalizeNullable(request.barcode()));
        product.setDescricao(normalizeNullable(request.description()));
        product.setPrecoVenda(request.salePrice());
        product.setCusto(request.cost());
        product.setEstoqueAtual(request.currentStock());
        product.setEstoqueMinimo(request.minimumStock());
        product.setCategoria(category);
        if (request.active() != null) {
            product.setAtivo(request.active());
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateStatus(Long id, boolean active) {
        Product product = findEntityById(id);
        product.setAtivo(active);
        return productMapper.toResponse(productRepository.save(product));
    }

    private Product findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
    }

    private void validateUniqueSku(String sku, Long currentId) {
        productRepository.findBySkuIgnoreCase(sku).ifPresent(existing -> {
            if (currentId == null || !existing.getId().equals(currentId)) {
                throw new ConflictException("Já existe um produto com este SKU");
            }
        });
    }

    private String normalizeName(String name) {
        return name.trim();
    }

    private String normalizeSku(String sku) {
        return sku.trim();
    }

    private String normalizeNullable(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }
}
