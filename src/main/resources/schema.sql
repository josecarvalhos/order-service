-- Criar o schema order_service
CREATE SCHEMA order_service;

-- Configurar o schema como padrão para o restante do script
SET search_path TO order_service;

-- Tabela de Pedidos
CREATE TABLE pedido (
    id SERIAL PRIMARY KEY,
    numero VARCHAR(255) UNIQUE NOT NULL,
    valor_total NUMERIC(15, 2) NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP 
);

-- Índices para melhorar o desempenho em consultas por status e número
CREATE INDEX idx_pedido_status ON pedido(status);
CREATE INDEX idx_pedido_numero ON pedido(numero);

-- Comentários para a tabela pedido
COMMENT ON COLUMN pedido.id IS 'Identificador único do pedido, gerado automaticamente.';
COMMENT ON COLUMN pedido.numero IS 'Número único do pedido, obrigatório e não pode ser duplicado.';
COMMENT ON COLUMN pedido.valor_total IS 'Valor total do pedido com até 15 dígitos, sendo 2 decimais; valor padrão é 0.';
COMMENT ON COLUMN pedido.status IS 'Status atual do pedido, como PENDENTE, APROVADO ou CANCELADO.';
COMMENT ON COLUMN pedido.data_criacao IS 'Data e hora de criação do pedido, com fuso horário.';
COMMENT ON COLUMN pedido.data_atualizacao IS 'Data e hora da última atualização do pedido, com fuso horário.';

-- Tabela de Produtos
CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco NUMERIC(15, 2) NOT NULL,
    estoque INT NOT NULL DEFAULT 0,
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Índices para consultas eficientes na tabela produto
CREATE INDEX idx_produto_nome ON produto(nome);
CREATE INDEX idx_produto_preco ON produto(preco);

-- Comentários para a tabela produto
COMMENT ON COLUMN produto.id IS 'Identificador único do produto, gerado automaticamente.';
COMMENT ON COLUMN produto.nome IS 'Nome do produto, obrigatório.';
COMMENT ON COLUMN produto.preco IS 'Preço unitário do produto, com até 15 dígitos, sendo 2 decimais.';
COMMENT ON COLUMN produto.estoque IS 'Quantidade disponível em estoque do produto.';
COMMENT ON COLUMN produto.data_criacao IS 'Data e hora de criação do produto.';
COMMENT ON COLUMN produto.data_atualizacao IS 'Data e hora da última atualização do produto.';

-- Tabela de Associação: Pedido_Produto
CREATE TABLE pedido_produto (
	id SERIAL PRIMARY KEY,
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    preco_unitario NUMERIC(15, 2) NOT NULL,
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
);

-- Índice para consultas eficientes na tabela de associação
CREATE INDEX idx_pedido_produto_pedido_id ON pedido_produto(pedido_id);
CREATE INDEX idx_pedido_produto_produto_id ON pedido_produto(produto_id);

-- Comentários para a tabela de associação
COMMENT ON COLUMN pedido_produto.pedido_id IS 'Identificador do pedido; chave estrangeira.';
COMMENT ON COLUMN pedido_produto.produto_id IS 'Identificador do produto; chave estrangeira.';
COMMENT ON COLUMN pedido_produto.quantidade IS 'Quantidade do produto no pedido.';
COMMENT ON COLUMN pedido_produto.preco_unitario IS 'Preço do produto no momento do pedido.';

INSERT INTO produto (nome, preco, estoque) 
VALUES ('Produto A', 50.00, 100),
       ('Produto B', 30.00, 200);
