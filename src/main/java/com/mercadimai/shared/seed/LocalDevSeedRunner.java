package com.mercadimai.shared.seed;

import com.mercadimai.category.entity.Category;
import com.mercadimai.category.repository.CategoryRepository;
import com.mercadimai.product.entity.Product;
import com.mercadimai.product.repository.ProductRepository;
import com.mercadimai.stock.entity.StockMovement;
import com.mercadimai.stock.enums.StockMovementType;
import com.mercadimai.stock.repository.StockMovementRepository;
import com.mercadimai.userprofile.entity.UserProfile;
import com.mercadimai.userprofile.enums.UserRole;
import com.mercadimai.userprofile.repository.UserProfileRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class LocalDevSeedRunner implements ApplicationRunner {

    private final UserProfileRepository userProfileRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    @Value("${app.seed.enabled:true}")
    private boolean seedEnabled;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!seedEnabled) {
            log.info("[SEED] app.seed.enabled=false. Seed local ignorada.");
            return;
        }

        UserProfile admin = upsertProfile("00000000-0000-4000-8000-000000000001", "Admin Mercadim", "admin@mercadim.ai", UserRole.ADMIN);
        UserProfile operador = upsertProfile("00000000-0000-4000-8000-000000000002", "Operador Mercadim", "operador@mercadim.ai", UserRole.OPERADOR);
        upsertProfile("00000000-0000-4000-8000-000000000003", "Caixa Mercadim", "caixa@mercadim.ai", UserRole.CAIXA);

        Category bebidas = upsertCategory("Bebidas");
        Category mercearia = upsertCategory("Mercearia");
        Category laticinios = upsertCategory("Laticínios");

        List<Product> products = List.of(
                upsertProduct("Água Mineral 500ml", "SKU-BEB-001", "789100000001", "Água sem gás", bd("2.50"), bd("1.20"), 120, 20, bebidas),
                upsertProduct("Refrigerante Cola 2L", "SKU-BEB-002", "789100000002", "Refrigerante sabor cola", bd("9.90"), bd("6.10"), 80, 10, bebidas),
                upsertProduct("Suco de Laranja 1L", "SKU-BEB-003", "789100000003", "Suco integral", bd("11.50"), bd("7.30"), 50, 8, bebidas),
                upsertProduct("Arroz Tipo 1 5kg", "SKU-MER-001", "789100000004", "Arroz branco tipo 1", bd("28.90"), bd("22.00"), 70, 10, mercearia),
                upsertProduct("Feijão Carioca 1kg", "SKU-MER-002", "789100000005", "Feijão carioca premium", bd("9.40"), bd("6.50"), 90, 15, mercearia),
                upsertProduct("Macarrão Espaguete 500g", "SKU-MER-003", "789100000006", "Massa de sêmola", bd("5.20"), bd("3.40"), 110, 20, mercearia),
                upsertProduct("Açúcar Refinado 1kg", "SKU-MER-004", "789100000007", "Açúcar refinado", bd("4.90"), bd("3.10"), 95, 15, mercearia),
                upsertProduct("Leite Integral 1L", "SKU-LAT-001", "789100000008", "Leite UHT integral", bd("5.60"), bd("3.90"), 100, 20, laticinios),
                upsertProduct("Queijo Mussarela 500g", "SKU-LAT-002", "789100000009", "Queijo fatiado", bd("21.90"), bd("16.00"), 40, 8, laticinios),
                upsertProduct("Iogurte Natural 170g", "SKU-LAT-003", "789100000010", "Iogurte natural integral", bd("3.80"), bd("2.40"), 75, 12, laticinios)
        );

        createInitialStockMovements(products.subList(0, 6), admin, operador);

        log.info("[SEED] Seed local finalizada com sucesso.");
        log.info("[SEED] IMPORTANTE: auth_user_id seed é de exemplo e deve casar com usuários reais do Supabase Auth para autenticação de verdade.");
    }

    private UserProfile upsertProfile(String authUserId, String nome, String email, UserRole role) {
        return userProfileRepository.findByAuthUserId(authUserId)
                .orElseGet(() -> userProfileRepository.save(UserProfile.builder()
                        .authUserId(authUserId)
                        .nome(nome)
                        .email(email.toLowerCase())
                        .role(role)
                        .ativo(true)
                        .build()));
    }

    private Category upsertCategory(String nome) {
        return categoryRepository.findByNomeIgnoreCase(nome)
                .orElseGet(() -> categoryRepository.save(Category.builder()
                        .nome(nome)
                        .ativo(true)
                        .build()));
    }

    private Product upsertProduct(
            String nome,
            String sku,
            String codigoBarras,
            String descricao,
            BigDecimal precoVenda,
            BigDecimal custo,
            int estoqueAtual,
            int estoqueMinimo,
            Category categoria
    ) {
        return productRepository.findBySku(sku)
                .orElseGet(() -> productRepository.save(Product.builder()
                        .nome(nome)
                        .sku(sku)
                        .codigoBarras(codigoBarras)
                        .descricao(descricao)
                        .precoVenda(precoVenda)
                        .custo(custo)
                        .estoqueAtual(estoqueAtual)
                        .estoqueMinimo(estoqueMinimo)
                        .ativo(true)
                        .categoria(categoria)
                        .build()));
    }

    private void createInitialStockMovements(List<Product> products, UserProfile admin, UserProfile operador) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String observacao = "SEED_ENTRADA_" + product.getSku();

            if (!stockMovementRepository.existsByObservacao(observacao)) {
                stockMovementRepository.save(StockMovement.builder()
                        .product(product)
                        .tipoMovimentacao(StockMovementType.ENTRADA)
                        .quantidade(product.getEstoqueAtual())
                        .observacao(observacao)
                        .userProfile(i % 2 == 0 ? admin : operador)
                        .build());
            }
        }
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
}
