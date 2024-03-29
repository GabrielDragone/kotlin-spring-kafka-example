package br.com.gabrieldragone.kotlinspringkafkaexample.client

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.LongSerializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

/**
 * A PROPRIEDADE "ENABLE_IDEMPOTENCE_CONFIG" É OPCIONAL E RESPONSÁVEL PRO ACIONAR A ORDENAÇÃO DO CONSUMO DAS MENSAGENS;
 * NA PASTA DOCKER ESTÁ O DOCKER-COMPOSE PARA RODAR OS SERVIÇOS LOCAIS;
 * O KAFDROP É UM GERENCIADOR DO KAFKA PARA NAVEGADOR, VOCÊ PODE CRIAR O TÓPICO POR LÁ;
 * CADA PARTIÇÃO DO TÓPICO SÓ PODE SER CONSUMIDO POR UM CONSUMIDOR, MAS UM CONSUMIDOR PODE CONSUMIR VÁRIAS PARTIÇÕES;
 * VOCÊ PODE ENVIAR A MSG PARA UMA PARTIÇÃO ESPECÍFICA, VERIFIQUE A CLASSE "ProducerRecord";
 *
 * REFERÊNCIA: https://github.com/confluentinc/examples/tree/6.2.0-post/clients/cloud/kotlin
 */

private val TOPIC = "topic1" // Se informarmos algum que não existe, o mesmo será criado e publicará as mensagens
private val BOOTSTRAP_SERVERS = "localhost:9091"

private fun createProducer(): KafkaProducer<Long?, String?> {
    val props = Properties()
    props[BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVERS
    props[CLIENT_ID_CONFIG] = "KafkaExampleProducer"
    props[KEY_SERIALIZER_CLASS_CONFIG] = LongSerializer::class.java.name
    props[VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
    return KafkaProducer(props)
}

fun main() {
    val kafkaProducer = createProducer()

    val numMessages = 10

    // `use` irá executar o bloco de código e fechar o Producer automaticamente
    kafkaProducer.use { producer ->
        repeat(numMessages) { i ->
            val key = null
            val record = "teste"
            println("Producing record: $key\t$record")

            producer.send(ProducerRecord(TOPIC, key, record)) { m: RecordMetadata, e: Exception? ->
                when (e) {
                    // no exception, good to go!
                    null -> println("Produced record to topic ${m.topic()} partition [${m.partition()}] @ offset ${m.offset()}")
                    // print stacktrace in case of exception
                    else -> e.printStackTrace()
                }
            }
        }

        producer.flush()
        println("$numMessages mensagens publicadas no tópico $TOPIC")
    }

}