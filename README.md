# Votação Pauta API
API para gerenciamento de sessões de votação.

#### [Acessar a API publicada via Swagger](http://ec2-52-67-250-221.sa-east-1.compute.amazonaws.com:8080/swagger-ui/index.html)
1. Incluir um Associado
2. Incluir uma Pauta
3. Abrir sessão para a Pauta
4. Votar na Pauta criada
5. Com o fechamento agendado, após o tempo denido na abertura da Pauta, será produzida uma mensagem na fila do Kafka

##### [Acessar o MongoExpress - usuário: mongouser, senha: mongopass](http://ec2-52-67-250-221.sa-east-1.compute.amazonaws.com:8081)
Base do projeto: votacao_api

##### [Acessar o Trifecta](http://ec2-52-67-250-221.sa-east-1.compute.amazonaws.com:8888)
Aba "Observe", tópico "votacao.pauta.api"

## Diretivas para execução

#### Criação do ambiente
Com o Docker em execução, execute o arquivo "docker_build.bat/docker_build.sh" para criação e configuração automática do ambiente.
- Build do projeto
- Execução de testes
- Download/Criação de Contêineres: Zookeeper, Kafka, MongoExpress, MongoDB, BackendV1(API de Votação)

#### MongoDB
Serviço do MongoDB executando localmente, ouvindo na porta padrão "27017".
Caso contrário, alterar o valor da propriedade "spring.data.mongodb.port" no arquivo "application.properties"
Obs.: O banco "votacao_api" será criado automaticamente, quando o seu usuário tiver permissões para criação de Documentos.

#### MongoExpress
Serviço web para acesso à base de dados MongoDB, acessível em http://localhost:8081 com usuário: mongouser, senha: mongopass.

#### Swagger
Com a aplicação em execução, estando o valor da propriedade "spring.profiles.active" no arquivo "application.properties" como "dev", acesse o endereço http://localhost:8080/swagger-ui/index.html. Podendo a partir desse, fazer uso da API de Votação de Pauta.

#### Kafka
Serviço do Apache Kafka localmente em execução ouvindo na porta tcp://localhost:9092, podendo ser alterada essa configuração, através da propriedade "kafka.broker-url" no arquivo "application.properties".
A mensagem será publicada no tópico de nome "votacao.pauta.api".

#### Trifecta
Serviço web para visualizar as mensagens enviadas ao Kafka.


## Escolha das tecnologias para o projeto
A escolha das tecnologias se deu pelo fato que são as mais utilizadas no mercado, indicando que são tecnologias maduras, com comunidade ativa e muito material disponível na web.

#### Spring Boot
Spring Boot na sua versão 2.3 para auxiliar na criação, configuração, desenvolvimento do projeto e gerenciamento de dependências.

#### JUnit
Junit na sua versão 5, para execução de testes unitários.

#### Mockito
Geração de dados falsos para execução de testes.

#### MongoDB
Banco de dados não relacional, escolhido por ter a possibilidade de não ficar preso a estrutura de modelos, podendo criar Documentos com estruturas diferentes sob demanda.

#### Swagger
Documentação automática, framework de fácil configuração para geração visual de endpoints.

#### Docker
Conteinerização que ajuda no desenvolvimento por ter o ambiente simulando um ambiente real, de fácil reconstrução.

#### Versionamento de API
Usaria através do mapeamento de Endpoints conforme utilizado nessa aplicação, sendo a versão parte do URI para chamar o recurso. Se por algum motivo arquitetural não essa opção seja aceita, utilizaria via Header X-API-Version.
