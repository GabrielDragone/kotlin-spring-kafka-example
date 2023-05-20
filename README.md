# kotlin-spring-kafka-example
Exemplo de aplicação utilizando Kotlin com Spring, Kafka e Docker Compose aprendidos no canal Black Lab Tech. Link do vídeo:
https://youtu.be/7nMDJzao31c

# Anotações:
* Kafka é uma ferramenta de PubSub/Mensageria utilizada para realizar comunicação entre microserviços de maneira assíncrona.
* Uma caracteristica interessante do Kafka, é que ele faz uma fila de mensagens que fica aguardando serem consumidas.
* Criado o docker-compose com 3 serviços:
  * zookeeper: Pré-requisito pra rodarmos o Kafka. Ele é responsável por criar e gerenciar tópicos, filas, conexões, etc.
  * kafka1: A PubSub.
  * kafdrop: Ferramenta de gerencimento do Kafka, para não precisarmos ficar enviando msg via terminal.

# Rodando a aplicação:
* Rodar os containers do docker-compose através da IDE ou por comandos:
  * cd docker
  * docker-compose up
* Acessar: http://localhost:9000/
* 