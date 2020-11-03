# Votação Pauta API
API para gerenciamento de sessões de votação.

## Diretivas para execução
### Banco de dados
Serviço do MySQL executando localmente, ouvindo na porta padrão "3306".
Caso contrário, alterar o valor da propriedade "spring.datasource.url" no arquivo "application.properties"
Obs.: O usuário de acesso ao banco deve possuir permissão para execução de comandos DML.

### Swagger
Com a aplicação em execução, acesse o endereço http://localhost:8080/swagger-ui.html. Podendo a partir desse, fazer uso da API de Votação de Pauta.

### Apache ActiveMQ
Serviço do Apache ActiveMQ localmente em execução ouvindo na porta tcp://localhost:61616, podendo ser alterada essa configuração, através da propriedade "spring.activemq.broker-url" no arquivo "application.properties".

### Apache AB
Com a API em execução, no diretório do executável "ab" do Apache execute:
ab -n 100000 -c 1000 http://localhost:8080/api/v1/associados/cpf/56592423080
Sem cache - Time taken for tests: 41.877 seconds
Com cache - Time taken for tests: 28.381 seconds

## Escolha das tecnologias para o projeto
### Spring Boot
Spring Boot na sua versão 2, por se tratar de um framework maduro e muito utilizado por grandes organizações.
Além disso ele auxilia na configuração do projeto, trazendo ganho de produtividade.

### JUnit
Junit na sua versão 5, para execução de testes unitários.

### Apache AB
Utilizado para teste de performance e estresse.

### Flyway
Adicionado suporte Flyway para ajudar no versionamento da base de dados.

### MySQL
Adicionado suporte ao MySQL, banco escolhido para execução do projeto.

### Swagger
Adicionado suporte para documentação das APIs.

### H2
Adicionado suporte ao banco em memória para facilitar a execução dos testes.

### EHCache
Adicionado e configurado o EHcache para execução mais performática do teste com Apache ab.
