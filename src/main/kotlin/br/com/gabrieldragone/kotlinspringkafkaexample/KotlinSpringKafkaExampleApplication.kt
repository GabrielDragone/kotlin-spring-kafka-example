package br.com.gabrieldragone.kotlinspringkafkaexample

import br.com.gabrieldragone.kotlinspringkafkaexample.entity.Pessoa
import br.com.gabrieldragone.kotlinspringkafkaexample.producer.PessoaProducerImpl
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringKafkaExampleApplication(
	val pessoaProducerImpl: PessoaProducerImpl
): ApplicationRunner {
	override fun run(args: ApplicationArguments?) {
		val pessoa = Pessoa("Gabriel", "Alves")
		pessoaProducerImpl.persist("25/10/1995", pessoa)
	}
}

fun main(args: Array<String>) {
	runApplication<KotlinSpringKafkaExampleApplication>(*args)
}
