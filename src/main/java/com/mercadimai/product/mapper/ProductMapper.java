package com.mercadimai.product.mapper;

import com.mercadimai.product.dto.ProductResponse;
import com.mercadimai.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getNome(),
                product.getSku(),
                product.getCodigoBarras(),
                product.getDescricao(),
                product.getPrecoVenda(),
                product.getCusto(),
                product.getEstoqueAtual(),
                product.getEstoqueMinimo(),
                product.isAtivo(),
                product.getCategoria().getId(),
                product.getCategoria().getNome(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
