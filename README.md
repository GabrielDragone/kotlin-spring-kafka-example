# kotlin-spring-kafka-example
* Exemplo de aplicação utilizando Kotlin com Spring, Kafka e Docker Compose aprendidos no canal Black Lab Tech. Link do vídeo:
  * https://youtu.be/7nMDJzao31c - Rodando Kafka com Docker
  * https://www.youtube.com/watch?v=8w_At1Krezc - Os 5 principais conceitos do Kafka (para iniciantes)

# Anotações:
* Kafka é uma plataforma de streaming de eventos (PubSub/Mensageria) utilizada para realizar comunicação entre microserviços de maneira assíncrona.
  * Publicar através do Publisher.
  * Kafka armazena.
  * Consumidor processa/recebe.
* Principais conceitos:
* ![img.png](img.png)
  * 1 - Evento (mensagem):
    * Composto por (exemplo): key (id ou cpf), value (dados cadastrados), timestamp (momento em que evento foi enviado) e header (duração, origem, *opcional).
  * 2 - Tópico:
    * Onde os eventos/mensagens são armazenados.
    * Particionado (no mínimo 1, no máximo qnd quisermos).
    * Ordenado (consome por ordem de chegada).
    * Fila durável (eventos persistentes). Diferente do RabbitMq, as msgs continuam no tópico durante um determinado periodo de tempo. Qnd criamos o tópico informamos a duração da mensagem. Só são apagadas qnd vencem.
  * 3 - Partições:
    * São as divisões do tópico.
    * Vantagens por escalabilidade (diferentes brokers, podem ficar em diferentes regiões).
    * Ordenação de consumo (key na mesma partição).
    * Máximo de 1 consumidor por partição (evita concorrência). Porém 1 consumidor pode consumir de mais de uma partição.
    * Necessário escalar quantidade de partições. Escalar de acordo com qntd de VM's.
  * 4 - Produtor:
    * Publicam os eventos do tópico. Essas mensagens são enviadas para as partições aleatoriamente, a não ser que já tenha alguma mensagem publicada com mesma key.
    * Não precisam conhecer o Consumidor (agnóstico).
    * Pode enviar msg ou lote (performance, acumula uma quantidade de mensagem e ai envia).
  * 5 - Consumidor:
    * Assinam, leem e processam os eventos/mensagens de um tópico.
    * Não conhecem o Produtor (agnóstico), apenas o tópico.
    * Offset & consumer group id, para sabermos onde paramos de consumir. Devido à fila ser durável, evitamos com offset de consumir mensagens duplicadas. Obs: RabbitMq consumiu, já apaga a mensagem.
    * Máximo de 1 consumidor por partição.
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