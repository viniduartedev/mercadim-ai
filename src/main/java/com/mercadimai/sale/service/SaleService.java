package com.mercadimai.sale.service;

import com.mercadimai.exception.ResourceNotFoundException;
import com.mercadimai.exception.BusinessException;
import com.mercadimai.sale.dto.SaleResponse;
import com.mercadimai.sale.enums.SaleStatus;
import com.mercadimai.sale.entity.Sale;
import com.mercadimai.sale.mapper.SaleMapper;
import com.mercadimai.sale.repository.SaleRepository;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Transactional(readOnly = true)
    public Page<SaleResponse> list(
            SaleStatus status,
            Long userProfileId,
            OffsetDateTime soldFrom,
            OffsetDateTime soldTo,
            Pageable pageable
    ) {
        if (soldFrom != null && soldTo != null && soldFrom.isAfter(soldTo)) {
            throw new BusinessException("Intervalo de venda inválido: soldFrom deve ser menor ou igual a soldTo");
        }

        Specification<Sale> spec = (root, query, cb) -> cb.conjunction();

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        if (userProfileId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userProfile").get("id"), userProfileId));
        }

        if (soldFrom != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("soldAt"), soldFrom));
        }

        if (soldTo != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("soldAt"), soldTo));
        }

        return saleRepository.findAll(spec, pageable).map(saleMapper::toSummaryResponse);
    }

    @Transactional(readOnly = true)
    public SaleResponse getById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));

        return saleMapper.toDetailResponse(sale);
    }
}
