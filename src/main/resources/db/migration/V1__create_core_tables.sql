CREATE TABLE IF NOT EXISTS user_profiles (
    id BIGSERIAL PRIMARY KEY,
    auth_user_id VARCHAR(64) NOT NULL,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    role VARCHAR(20) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ,
    CONSTRAINT uk_user_profiles_auth_user_id UNIQUE (auth_user_id),
    CONSTRAINT uk_user_profiles_email UNIQUE (email),
    CONSTRAINT ck_user_profiles_role CHECK (role IN ('ADMIN', 'OPERADOR', 'CAIXA'))
);

CREATE INDEX IF NOT EXISTS idx_user_profiles_email ON user_profiles (email);

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ,
    CONSTRAINT uk_categories_nome UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sku VARCHAR(60) NOT NULL,
    codigo_barras VARCHAR(50),
    descricao VARCHAR(600),
    preco_venda NUMERIC(12,2) NOT NULL,
    custo NUMERIC(12,2),
    estoque_atual INTEGER NOT NULL DEFAULT 0,
    estoque_minimo INTEGER NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ,
    CONSTRAINT uk_products_sku UNIQUE (sku),
    CONSTRAINT fk_products_categoria FOREIGN KEY (categoria_id) REFERENCES categories (id),
    CONSTRAINT ck_products_preco_venda_non_negative CHECK (preco_venda >= 0),
    CONSTRAINT ck_products_custo_non_negative CHECK (custo IS NULL OR custo >= 0),
    CONSTRAINT ck_products_estoque_atual_non_negative CHECK (estoque_atual >= 0),
    CONSTRAINT ck_products_estoque_minimo_non_negative CHECK (estoque_minimo >= 0)
);

CREATE INDEX IF NOT EXISTS idx_products_sku ON products (sku);
CREATE INDEX IF NOT EXISTS idx_products_categoria_id ON products (categoria_id);
CREATE INDEX IF NOT EXISTS idx_products_nome ON products (nome);

CREATE TABLE IF NOT EXISTS stock_movements (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    tipo_movimentacao VARCHAR(30) NOT NULL,
    quantidade INTEGER NOT NULL,
    observacao VARCHAR(300),
    user_profile_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_stock_movements_product FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_stock_movements_user_profile FOREIGN KEY (user_profile_id) REFERENCES user_profiles (id),
    CONSTRAINT ck_stock_movements_tipo CHECK (tipo_movimentacao IN ('ENTRADA', 'AJUSTE', 'SAIDA_VENDA')),
    CONSTRAINT ck_stock_movements_quantidade_non_zero CHECK (quantidade <> 0)
);

CREATE INDEX IF NOT EXISTS idx_stock_movements_product_id ON stock_movements (product_id);
CREATE INDEX IF NOT EXISTS idx_stock_movements_user_profile_id ON stock_movements (user_profile_id);
CREATE INDEX IF NOT EXISTS idx_stock_movements_created_at ON stock_movements (created_at);

CREATE TABLE IF NOT EXISTS sales (
    id BIGSERIAL PRIMARY KEY,
    numero_venda VARCHAR(40) NOT NULL,
    valor_total NUMERIC(12,2) NOT NULL,
    forma_pagamento VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    user_profile_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_sales_numero_venda UNIQUE (numero_venda),
    CONSTRAINT fk_sales_user_profile FOREIGN KEY (user_profile_id) REFERENCES user_profiles (id),
    CONSTRAINT ck_sales_valor_total_non_negative CHECK (valor_total >= 0),
    CONSTRAINT ck_sales_forma_pagamento CHECK (forma_pagamento IN ('DINHEIRO', 'PIX', 'CARTAO')),
    CONSTRAINT ck_sales_status CHECK (status IN ('ABERTA', 'FINALIZADA', 'CANCELADA'))
);

CREATE INDEX IF NOT EXISTS idx_sales_user_profile_id ON sales (user_profile_id);
CREATE INDEX IF NOT EXISTS idx_sales_created_at ON sales (created_at);

CREATE TABLE IF NOT EXISTS sale_items (
    id BIGSERIAL PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL,
    CONSTRAINT fk_sale_items_sale FOREIGN KEY (sale_id) REFERENCES sales (id) ON DELETE CASCADE,
    CONSTRAINT fk_sale_items_product FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT ck_sale_items_quantidade_positive CHECK (quantidade > 0),
    CONSTRAINT ck_sale_items_preco_unitario_non_negative CHECK (preco_unitario >= 0),
    CONSTRAINT ck_sale_items_subtotal_non_negative CHECK (subtotal >= 0)
);

CREATE INDEX IF NOT EXISTS idx_sale_items_sale_id ON sale_items (sale_id);
CREATE INDEX IF NOT EXISTS idx_sale_items_product_id ON sale_items (product_id);
