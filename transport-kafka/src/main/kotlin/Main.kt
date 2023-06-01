package ru.otus.otuskotlin.transport.kafka

import ru.otus.otuskotlin.transport.AppKafkaConfig
import ru.otus.otuskotlin.transport.AppKafkaPaymentConsumer
import ru.otus.otuskotlin.transport.AppKafkaSubscriptionConsumer

fun main() {
    val config = AppKafkaConfig()
    val subscriptionConsumer = AppKafkaSubscriptionConsumer(config, listOf(SubscriptionConsumerStrategy()))
    subscriptionConsumer.run()
    val paymentConsumer = AppKafkaPaymentConsumer(config, listOf(PaymentConsumerStrategy()))
    paymentConsumer.run()
}