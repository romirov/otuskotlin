package ru.otus.otuskotlin.transport.kafka

import org.otus.otuskotlin.api.v1.models.PaymentRequest
import org.otus.otuskotlin.api.v1.models.PaymentResponse
import ru.otus.otuskotlin.api.apiV1RequestPaymentDeserialize
import ru.otus.otuskotlin.api.apiV1ResponseSerialize
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.mappers.fromPaymentTransport
import ru.otus.otuskotlin.mappers.toTransportPayment
import ru.otus.otuskotlin.transport.AppKafkaConfig
import ru.otus.otuskotlin.transport.ConsumerStrategy
import ru.otus.otuskotlin.transport.InputOutputTopics

class PaymentConsumerStrategy : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicPaymentIn, config.kafkaTopicPaymentOut)
    }

    override fun serialize(source: Context): String {
        val response: PaymentResponse = source.toTransportPayment()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: Context) {
        val request: PaymentRequest = apiV1RequestPaymentDeserialize(value)
        target.fromPaymentTransport(request)
    }
}