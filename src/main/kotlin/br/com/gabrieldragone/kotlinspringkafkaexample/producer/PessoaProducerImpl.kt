package br.com.gabrieldragone.kotlinspringkafkaexample.producer

import br.com.gabrieldragone.kotlinspringkafkaexample.entity.Pessoa
import br.com.gabrieldragone.kotlinspringkafkaexample.entity.PessoaDTO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import java.time.LocalDate

@Component
class PessoaProducerImpl (
    private val pessoaTemplate: KafkaTemplate<String, PessoaDTO>
) {

    val topicName = "Pessoa"

    fun persist(messageId: String, payload: Pessoa) {
        val dto = createDTO(payload)
        sendPessoaMessage(messageId, dto)
    }

    private fun sendPessoaMessage(messageId: String, dto: PessoaDTO) {
        val message = createMessageWithHeaders(messageId, dto, topicName)

        // Parecido com a promise do js:
        val future: ListenableFuture<SendResult<String, PessoaDTO>> = pessoaTemplate.send(message)

        future.addCallback(object: ListenableFutureCallback<SendResult<String, PessoaDTO>> {
            override fun onSuccess(result: SendResult<String, PessoaDTO>?) { // Se for sucesso:
                println("Mensagem publicada. MessageId $messageId")
            }
            override fun onFailure(ex: Throwable) { // Se der erro:
                println("Erro na publicação da mensagem. MessageId $messageId")
            }
        })

    }

    private fun createMessageWithHeaders(messageId: String, pessoaDTO: PessoaDTO, topicName: String): Message<PessoaDTO> {
        return MessageBuilder.withPayload(pessoaDTO)
            .setHeader("hash", pessoaDTO.hashCode())
            .setHeader("version", "1.0.0")
            .setHeader("endOfLife", LocalDate.now().plusDays(1L)) // Tempo de vida
            .setHeader("type", "fct")
            .setHeader("cid", messageId)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .setHeader(KafkaHeaders.MESSAGE_KEY, messageId)
            .build()
    }

    private fun createDTO(payload: Pessoa): PessoaDTO {
        return PessoaDTO
            .newBuilder()
            .setNome(payload.nome)
            .setSobrenome(payload.sobrenome)
            .build()
    }

}