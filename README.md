# Votação Pauta API
API para gerenciamento de sessões de votação.

## Diretivas para execução

### Criação do ambiente
Com o Docker em execução, execute o arquivo "docker_build.bat" para criação e configuração automática do ambiente.
- Build do projeto
- Execução de testes
- Download/Criação de Contêineres: Zookeeper, Kafka, MongoExpress, MongoDB, BackendV1(API de Votação)

### MongoDB
Serviço do MongoDB executando localmente, ouvindo na porta padrão "27017".
Caso contrário, alterar o valor da propriedade "spring.data.mongodb.port" no arquivo "application.properties"
Obs.: O banco "votacao_api" será criado automaticamente, quando o seu usuário tiver permissões para criação de Documentos.

### MongoExpress
Serviço web para acesso à base de dados MongoDB, acessível em http://localhost:8081 com usuário: mongouser, senha: mongopass.

### Swagger
Com a aplicação em execução, estando o valor da propriedade "spring.profiles.active" no arquivo "application.properties" como "dev", acesse o endereço http://localhost:8080/swagger-ui/index.html. Podendo a partir desse, fazer uso da API de Votação de Pauta.

### Kafka
Serviço do Apache Kafka localmente em execução ouvindo na porta tcp://localhost:9092, podendo ser alterada essa configuração, através da propriedade "kafka.broker-url" no arquivo "application.properties".
A mensagem será publicada no tópico de nome "votacao.pauta.api".

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

### Mockito
Geração de dados falsos para execução de testes.

### Apache AB
Utilizado para teste de performance e estresse.

### MongoDB
Banco de dados não relacional, escolhido por ter a possibilidade de não ficar preso a estrutura de modelos, podendo criar Documentos
com estruturas diferentes sob demanda.

### Swagger
Documentação automática, framework de fácil configuração para geração visual de endpoints.

### EHCache
Adicionado e configurado o EHcache para execução mais performática do teste com Apache ab.

###Docker
Conteinerização que ajuda no desenvolvimento por ter o ambiente simulando um ambiente real, de fácil reconstrução.

### Versionamento de API
Usaria através do mapeamento de Endpoints conforme utilizado nessa aplicação, sendo a versão parte do URI para chamar o recurso. Se por algum motivo arquitetural não essa opção seja aceita, utilizaria via Header X-API-Version.
