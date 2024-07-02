CREATE TABLE respostas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensagem VARCHAR(255),
    data_criacao TIMESTAMP,
    solucao BOOLEAN,
    topico_id BIGINT,
    FOREIGN KEY (topico_id) REFERENCES topicos(id)
);
