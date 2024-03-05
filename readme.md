## Camisetas "Lavou, Sumiu!"

Projeto criado para a avaliação final do módulo de Microsserviços da minha pós-graduação em Arquitetura de Software.

Serão criados 4 microsserviços que tem as seguintes funcionalidades:

* Pedidos
  * Criação de pedidos (*)
  * Consulta de pedidos (*)
  * Cancelamento de pedidos
  * Consulta de status de pedidos
* Produtos
  * Cadastro de produtos (*)
  * Criação de Carrinho de Pedido (*)
  * Atualização de Carrinho de Pedido (*)
  * Consulta de produtos
  * Atualização de produtos
* Pagamentos
  * Criação de pagamentos (*)
  * Consulta de pagamentos (*)
* Notificação
  * Envio de Email (*)

A princípio a comunicação entre os microsserviços será feita através do protocolo HTTP, com exceção do microsserviço de Notificação que será feita através de mensageria com RabbitMQ.


## Tecnologias

* Java 17 (*)
* Spring Boot (*)
* RabbitMQ (Notificação) (*)
* Docker

PS: os itens com (*) são obrigatórios, os demais são apenas idéias que não me comprometo, mas que seriam legais para aprender.


