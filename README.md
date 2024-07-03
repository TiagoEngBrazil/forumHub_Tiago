# Fórum Hub

## Descrição

Fórum Hub é um projeto desenvolvido como um desafio para graduação no programa ONE (Oracle Next Education). Este projeto visa replicar a funcionalidade do Fórum da Alura, onde os usuários podem trocar conhecimentos, criar tópicos para solucionar dúvidas e ajudar outros usuários com suas perguntas.

## Funcionalidades

- **Criação de Tópicos:** Usuários cadastrados podem criar novos tópicos contendo um título e uma mensagem. Cada tópico pertence a um curso específico.
- **Listagem de Tópicos:** Possibilidade de listar todos os tópicos cadastrados no banco de dados.
- **Visualização de Tópico:** Visualizar os detalhes de um tópico específico.
- **Autenticação e Autorização:** Somente usuários autenticados podem criar, editar ou deletar tópicos, garantindo a segurança e integridade dos dados.
- **Criação de Respostas:** Usuários cadastrados podem criar novas respostas para tópicos contendo uma mensagem.
- **Listagem de Respostas:** Possibilidade de listar todos as respostas de tópicos cadastrados no banco de dados.
- **Visualização de Tópico:** Visualizar os detalhes de uma resposta específico.
- **Autenticação e Autorização:** Somente usuários autenticados podem criar, editar ou deletar respostas, garantindo a segurança e integridade dos dados.
- **Registro de Usuários:** Registro de novos usuários com geração de token JWT para autenticação.

## Requisitos de Negócio

1. **Usuários Cadastrados:** Apenas usuários registrados podem criar, editar ou deletar tópicos e respostas.
2. **Autenticação:** Utilização de tokens Bearer para autenticação nas requisições.
3. **Segurança:** Apenas o autor do tópico ou resposta pode editá-lo ou deletá-lo.
4. **Estrutura do Tópico:** Cada tópico deve ter um título, uma mensagem, pertencer a um curso e ter uma data de criação gerada automaticamente.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.0**
- **MySQL**
- **Hibernate**
- **JWT (JSON Web Token)**
- **Lombok**
- **Flyway**
- **Spring Security**
- **Spring Data JPA**
- **Springdoc OpenAPI**

## Endpoints Principais

- **Registrar Usuário:** `POST /usuarios`
- **Atualizar Usuário:** `POST /usuarios`
- **Registrar Usuário:** `DELETE /usuarios`
- **Autenticar Usuário:** `POST /login`
- **Criar Tópico:** `POST /topicos`
- **Listar Tópicos:** `GET /topicos`
- **Visualizar um Tópico Específico:** `GET /topicos/{id}`
- **Editar Tópico:** `PUT /topicos/{id}`
- **Deletar Tópico:** `DELETE /topicos/{id}`
- **Criar Resposta:** `POST /topicos/{id}/respostas`
- **Listar Respostas de um Tópico:** `GET /topicos{id}/respostas`
- **Visualizar Respostas de um Tópico Específico:** `GET /topicos/{id}/respostas/{id2}`
- **Editar Resposta:** `PUT /topicos/{id}/respostas/{id2}`
- **Deletar Resposta:** `DELETE /topicos/{id}/respostas/{id2}`

## Autor

Tiago Lima Pimenta
