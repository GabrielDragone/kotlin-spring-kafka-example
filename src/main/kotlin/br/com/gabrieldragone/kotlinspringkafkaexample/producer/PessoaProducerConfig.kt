package br.com.gabrieldragone.kotlinspringkafkaexample.producer

import br.com.gabrieldragone.kotlinspringkafkaexample.entity.PessoaDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class PessoaProducerConfig {

    // Obs: Como não tem nenhum Props (ver exemplo ProducerExample) setado abaixo, essas configurações são buscadas diretamente do application.properties.
    @Bean // Quando subirmos o Spring, ele vai instanciar o templete abaixo.
    fun pessoaDTOTemplate(factory: ProducerFactory<String, PessoaDTO>): KafkaTemplate<String, PessoaDTO> {
        return KafkaTemplate(factory)
    }

}