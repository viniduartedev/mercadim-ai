package com.mercadimai.sale.mapper;

import com.mercadimai.sale.dto.SaleItemResponse;
import com.mercadimai.sale.dto.SaleResponse;
import com.mercadimai.sale.entity.Sale;
import com.mercadimai.sale.entity.SaleItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    public SaleResponse toSummaryResponse(Sale sale) {
        return new SaleResponse(
                sale.getId(),
                sale.getStatus(),
                sale.getTotal(),
                sale.getNotes(),
                sale.getSoldAt(),
                sale.getUserProfile() != null ? sale.getUserProfile().getId() : null,
                sale.getUserProfile() != null ? sale.getUserProfile().getNome() : null,
                List.of(),
                sale.getCreatedAt(),
                sale.getUpdatedAt()
        );
    }

    public SaleResponse toDetailResponse(Sale sale) {
        Long userProfileId = sale.getUserProfile() != null ? sale.getUserProfile().getId() : null;
        String userProfileName = sale.getUserProfile() != null ? sale.getUserProfile().getNome() : null;

        List<SaleItemResponse> items = sale.getItems().stream()
                .map(this::toItemResponse)
                .toList();

        return new SaleResponse(
                sale.getId(),
                sale.getStatus(),
                sale.getTotal(),
                sale.getNotes(),
                sale.getSoldAt(),
                userProfileId,
                userProfileName,
                items,
                sale.getCreatedAt(),
                sale.getUpdatedAt()
        );
    }

    private SaleItemResponse toItemResponse(SaleItem item) {
        return new SaleItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getNome(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }
}
