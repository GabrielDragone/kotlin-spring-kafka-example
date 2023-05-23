package br.com.gabrieldragone.kotlinspringkafkaexample.producer

import br.com.gabrieldragone.kotlinspringkafkaexample.entity.Pessoa
import br.com.gabrieldragone.kotlinspringkafkaexample.entity.PessoaDTO
import com.google.common.util.concurrent.ListenableFuture
import org.apache.logging.log4j.message.Message
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

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
        val message = createMessateWithHeaders(messageId, dto, topicName)

        val future: ListenableFuture<SendResult<String, PessoaDTO>> = pessoaTemplate.send(message)

    }

    private fun createMessateWithHeaders(messageId: String, pessoaDTO: PessoaDTO, topicName: String): Message<PessoaDTO> {
        return MessageBuilder.withPayload(pessoaDTO)
            .setHeader("hash", pessoaDTO.hashCode())
            .setHeader("version", "1.0.0")
            .setHeader("endOfLife", LocalDate.now().plusDays(1L))
            .setHeader("type", "fct")
            .setHeader("cid", messageId)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .setHeader(KafkaHeaders.KEY, messageId)
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