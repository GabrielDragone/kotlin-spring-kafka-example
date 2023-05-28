package br.com.gabrieldragone.kotlinspringkafkaexample.controller

import br.com.gabrieldragone.kotlinspringkafkaexample.entity.Pessoa
import br.com.gabrieldragone.kotlinspringkafkaexample.producer.PessoaProducerImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/hello-worlds")
class HelloWorldController(val pessoaProducerImpl: PessoaProducerImpl) {

    @GetMapping
    fun helloWorld(): String {
        val pessoa = Pessoa("Gabriel", "Alves")
        pessoaProducerImpl.persist("25/10/1995", pessoa)
        return "Hello world"
    }

}