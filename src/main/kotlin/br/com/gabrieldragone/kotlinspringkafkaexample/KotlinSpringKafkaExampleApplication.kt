package br.com.gabrieldragone.kotlinspringkafkaexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringKafkaExampleApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringKafkaExampleApplication>(*args)
}
