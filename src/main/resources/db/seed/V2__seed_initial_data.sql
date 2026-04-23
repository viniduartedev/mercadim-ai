begin;

-- =========================================================
-- 1) CATEGORIAS
-- =========================================================
insert into categories (nome, ativo, created_at, updated_at)
values
    ('Bebidas', true, now(), now()),
    ('Mercearia', true, now(), now()),
    ('Laticínios', true, now(), now()),
    ('Padaria', true, now(), now()),
    ('Higiene', true, now(), now())
on conflict (nome) do nothing;

-- =========================================================
-- 2) PRODUTOS
-- =========================================================
insert into products
    (nome, sku, codigo_barras, descricao, preco_venda, custo, estoque_atual, estoque_minimo, ativo, categoria_id, created_at, updated_at)
values
    (
        'Coca-Cola 2L',
        'BEB-0001',
        '7894900011517',
        'Refrigerante Coca-Cola 2 litros',
        11.99,
        8.20,
        30,
        5,
        true,
        (select id from categories where nome = 'Bebidas'),
        now(),
        now()
    ),
    (
        'Guaraná Antarctica 2L',
        'BEB-0002',
        '7891991000834',
        'Refrigerante Guaraná Antarctica 2 litros',
        9.49,
        6.90,
        25,
        5,
        true,
        (select id from categories where nome = 'Bebidas'),
        now(),
        now()
    ),
    (
        'Água Mineral 500ml',
        'BEB-0003',
        '7896004400919',
        'Água mineral sem gás 500ml',
        2.50,
        1.20,
        60,
        10,
        true,
        (select id from categories where nome = 'Bebidas'),
        now(),
        now()
    ),
    (
        'Arroz 5kg',
        'MER-0001',
        '7896006716117',
        'Arroz branco tipo 1 - 5kg',
        27.90,
        21.40,
        20,
        4,
        true,
        (select id from categories where nome = 'Mercearia'),
        now(),
        now()
    ),
    (
        'Feijão Carioca 1kg',
        'MER-0002',
        '7896006711143',
        'Feijão carioca tipo 1 - 1kg',
        8.99,
        6.30,
        35,
        6,
        true,
        (select id from categories where nome = 'Mercearia'),
        now(),
        now()
    ),
    (
        'Macarrão Espaguete 500g',
        'MER-0003',
        '7896022201236',
        'Macarrão espaguete 500g',
        4.79,
        2.90,
        40,
        8,
        true,
        (select id from categories where nome = 'Mercearia'),
        now(),
        now()
    ),
    (
        'Leite Integral 1L',
        'LAT-0001',
        '7891025101588',
        'Leite integral UHT 1 litro',
        5.99,
        4.10,
        45,
        10,
        true,
        (select id from categories where nome = 'Laticínios'),
        now(),
        now()
    ),
    (
        'Queijo Mussarela 200g',
        'LAT-0002',
        '7891000315504',
        'Queijo mussarela fatiado 200g',
        9.90,
        6.80,
        18,
        4,
        true,
        (select id from categories where nome = 'Laticínios'),
        now(),
        now()
    ),
    (
        'Pão de Forma',
        'PAD-0001',
        '7896002300105',
        'Pão de forma tradicional',
        7.49,
        4.90,
        22,
        5,
        true,
        (select id from categories where nome = 'Padaria'),
        now(),
        now()
    ),
    (
        'Sabonete 85g',
        'HIG-0001',
        '7891150067016',
        'Sabonete corporal 85g',
        2.99,
        1.60,
        50,
        10,
        true,
        (select id from categories where nome = 'Higiene'),
        now(),
        now()
    )
on conflict (sku) do nothing;

-- =========================================================
-- 3) USER_PROFILES
-- Base para o fluxo com Supabase Auth
-- auth_user_id aqui é de exemplo; depois você troca pelos IDs reais
-- =========================================================
insert into user_profiles
    (auth_user_id, nome, email, role, ativo, created_at, updated_at)
values
    ('supabase-admin-id-001', 'Administrador Mercadim', 'admin@mercadim.ai', 'ADMIN', true, now(), now()),
    ('supabase-operador-id-001', 'Operador Mercadim', 'operador@mercadim.ai', 'OPERADOR', true, now(), now()),
    ('supabase-caixa-id-001', 'Caixa Mercadim', 'caixa@mercadim.ai', 'CAIXA', true, now(), now())
on conflict (auth_user_id) do nothing;

-- Se quiser também evitar conflito por email, pode rodar depois:
-- update user_profiles set updated_at = now() where email in ('admin@mercadim.ai','operador@mercadim.ai','caixa@mercadim.ai');

commit;
