CREATE TABLE endereco (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    logradouro VARCHAR(255) NOT NULL,
    numero INT NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(20) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    pais VARCHAR(50) NOT NULL
);

CREATE TABLE instituicao (
    id BIGSERIAL PRIMARY KEY,
    razao_social VARCHAR(255) NOT NULL UNIQUE,
    nome_fantasia VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefone_fixo VARCHAR(20) UNIQUE,
    telefone_celular VARCHAR(20) UNIQUE,
    cnpj VARCHAR(20) UNIQUE,
    tipo_instituicao VARCHAR(50) NOT NULL,
    endereco_id BIGINT,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    instituicao_id BIGINT NOT NULL,
    FOREIGN KEY (instituicao_id) REFERENCES instituicao(id) ON DELETE CASCADE
);

CREATE TABLE hardware (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    codigo_patrimonial VARCHAR(50) NOT NULL UNIQUE,
    componente VARCHAR(50) NOT NULL,
    numero_serial VARCHAR(100) UNIQUE,
    modelo VARCHAR(100),
    fabricante VARCHAR(100),
    velocidade VARCHAR(20),
    capacidade_armazenamento VARCHAR(50),
    data_fabricacao TIMESTAMP,
    preco_total NUMERIC(10,2),
    estatus VARCHAR(20) NOT NULL,
    voltagem NUMERIC(5,2),
    imagem_url VARCHAR(255),
    instituicao_id BIGINT NOT NULL,
    FOREIGN KEY (instituicao_id) REFERENCES instituicao(id) ON DELETE CASCADE
);
