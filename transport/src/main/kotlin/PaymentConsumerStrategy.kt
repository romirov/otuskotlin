package ru.otus.otuskotlin.transport

import Context
import org.otus.otuskotlin.api.v1.models.PaymentRequest
import org.otus.otuskotlin.api.v1.models.PaymentResponse
import org.otus.otuskotlin.api.v1.models.SubscriptionRequest
import org.otus.otuskotlin.api.v1.models.SubscriptionResponse
import ru.otus.otuskotlin.api.apiV1RequestPaymentDeserialize
import ru.otus.otuskotlin.api.apiV1RequestSubscriptionDeserialize
import ru.otus.otuskotlin.api.apiV1ResponseSerialize
import ru.otus.otuskotlin.mappers.fromPaymentTransport
import ru.otus.otuskotlin.mappers.fromSubscriptionTransport
import ru.otus.otuskotlin.mappers.toTransportPayment
import ru.otus.otuskotlin.mappers.toTransportSubscription

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