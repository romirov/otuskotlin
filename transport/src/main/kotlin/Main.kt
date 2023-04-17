package ru.otus.otuskotlin.transport

fun main() {
    val config = AppKafkaConfig()
    val subscriptionConsumer = AppKafkaSubscriptionConsumer(config, listOf(SubscriptionConsumerStrategy()))
    subscriptionConsumer.run()
    val paymentConsumer = AppKafkaPaymentConsumer(config, listOf(PaymentConsumerStrategy()))
    paymentConsumer.run()
}