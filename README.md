# Order

##### Para subir o docker rodar o seguinte comando na raíz do projeto:
`docker-compose up`

##### Tecnologias Utilizadas

MacOS Ventura 13.2.1

IntelliJ IDEA 2022.3 (Community Edition)

Java 17

Spring Boot 2.7.16

MySQL version latest

RabbitMQ version management-alpine

### Arquitetura

Resolvi fazer a arquitetura diretamente na em Cloud

![modelo_cloud.png](..%2Fmodelo_cloud.png)

Coloquei um API Gateway para um melhor controle e versionamento da API e ao invés de provisionar um RDS MySQL, optei pelo Aurora MySQL que alem de ser mais performático é também mais barato.

### Modelagem Banco de Dados

![modelo_db.png](..%2Fmodelo_db.png)

### Evidencia de testes 

![request_1.png](..%2Frequest_1.png)
![request_2.png](..%2Frequest_2.png)
![request_3.png](..%2Frequest_3.png)

### Referencias:

https://www.baeldung.com/spring-amqp
https://www.baeldung.com/rabbitmq
https://medium.com/xp-inc/rabbitmq-com-docker-conhecendo-o-admin-cc81f3f6ac3b
https://calculator.aws/#/

###### Considerações Finais

Se eu tivesse mais tempo pra refatorar o codigo, dividiria o projeto em modulos, separando a entrada via mensageria da API Rest, desacoplando.
Inicialmente eu ia criar apenas um endpoint usando query param, mas na execução na minha cabeça fez mais sentido criar separado e cada consulta por path param, mapeando as respostas separademente de acordo com a consulta.
