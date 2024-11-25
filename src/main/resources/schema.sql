-- Criar o schema order_service
CREATE SCHEMA order_service;

-- Configurar o schema como padrão para o restante do script
SET search_path TO order_service;

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    numero VARCHAR(255) UNIQUE NOT NULL,
    valor_total NUMERIC(15, 2) NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP 
);

-- Índices para melhorar o desempenho em consultas por status e número
CREATE INDEX idx_pedidos_status ON pedidos(status);
CREATE INDEX idx_pedidos_numero ON pedidos(numero);

-- Adicionando comentários às colunas
COMMENT ON COLUMN pedidos.id IS 'Identificador único do pedido, gerado automaticamente.';
COMMENT ON COLUMN pedidos.numero IS 'Número único do pedido, obrigatório e não pode ser duplicado.';
COMMENT ON COLUMN pedidos.valor_total IS 'Valor total do pedido com até 15 dígitos, sendo 2 decimais; valor padrão é 0.';
COMMENT ON COLUMN pedidos.status IS 'Status atual do pedido, como PENDENTE, APROVADO ou CANCELADO.';
COMMENT ON COLUMN pedidos.data_criacao IS 'Data e hora de criação do pedido, com fuso horário.';
COMMENT ON COLUMN pedidos.data_atualizacao IS 'Data e hora da última atualização do pedido, com fuso horário.';

-- Criar a tabela de produtos
CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    pedido_id INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    preco NUMERIC(15, 2) NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE
);

-- Índice para consultas eficientes por pedido
CREATE INDEX idx_produtos_pedido_id ON produtos(pedido_id);

-- Índice composto para melhorar consultas por número e status de pedidos
CREATE INDEX idx_pedidos_numero_status ON pedidos(numero, status);

-- Adicionando comentários às colunas da tabela produtos
COMMENT ON COLUMN produtos.id IS 'Identificador único do produto, gerado automaticamente.';
COMMENT ON COLUMN produtos.pedido_id IS 'ID do pedido ao qual o produto está associado; chave estrangeira.';
COMMENT ON COLUMN produtos.nome IS 'Nome do produto, obrigatório.';
COMMENT ON COLUMN produtos.preco IS 'Preço unitário do produto, com até 15 dígitos, sendo 2 decimais.';
COMMENT ON COLUMN produtos.quantidade IS 'Quantidade do produto no pedido; valor padrão é 1.';
