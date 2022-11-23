# Sobre o Projeto
Projeto de votação criado durante desafio.

## Requisitos
- Java 8 ou superior;
- Postgres 10 ou superior.

## Como faz pra rodar?
- Baixar os fontes;
- Importar eles na sua IDE de preferência;
  - Se for o VSCode, instalar os complementos abaixo:
    - Spring Initializr Java Support (id: vscjava.vscode-spring-initializr);
    - Lombok Annotations Support for VS Code (id: vscjava.vscode-lombok);
- Criar o banco de dados **votacao**:
  - Ao rodar o projeto, as tabelas serão criadas/ajustadas automaticamente e mantidas após o projeto parar de rodar.
  - As configurações de conexão com o banco de dados estão em *src/main/resources/application.properties*;
- Colocar pra rodar/debugar usando sua IDE de preferência;
- Acessar o swagger: 
  - http://localhost:8080/swagger-ui/

