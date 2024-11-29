# order-service
Serviço de gerenciamento de pedidos

## Plano de Implementação para o Serviço "Order"
O serviço "Order" será responsável por processar pedidos recebidos da API externa A, realizar cálculos e enviar os pedidos processados para a API externa B, utilizando Kafka para mensageria e seguindo as especificações propostas. Abaixo, descrevo cada etapa de implementação:

###1. Tecnologias Utilizadas
- **Linguagem:** Java 21.
- **Framework:** Spring Boot 3.
- **Banco de Dados:** PostgreSQL.
- **Mensageria:** Kafka.
- **Documentação:** Swagger.
- **CI/CD:** github actions.
- **Contêinerização:** Docker.
- **Orquestração:** kubernetes.


###2. Estrutura do Serviço
**Camadas**
- **API:**
	- Endpoints REST para integração e documentação via Swagger.
	
	
- **Serviço:**
	- Processamento de pedidos (cálculos e verificação de duplicação).
	
	
- **Repositório:**
	- Persistência de dados no PostgreSQL.
	
	
- **Mensageria:**
	- Consumo e publicação de mensagens usando Kafka.
	
	
###3. Testes
- **Unitários:**
	- JUnit 5 para testar serviços, repositórios e consumidores Kafka.


- **Integração:**
	- Testes de integração com bancos de dados h2 em memória e Kafka (usando Testcontainers).


- **Swagger UI:**
	- Permita testes manuais pela interface do Swagger.
		- http://localhost:8080/order/swagger-ui/index.html