package ru.otus.otuskotlin.transport

import Context
import models.Subscription
import org.otus.otuskotlin.api.v1.models.SubscriptionRequest
import org.otus.otuskotlin.api.v1.models.SubscriptionResponse
import ru.otus.otuskotlin.api.apiV1RequestSubscriptionDeserialize
import ru.otus.otuskotlin.api.apiV1ResponseSerialize
import ru.otus.otuskotlin.mappers.fromSubscriptionTransport
import ru.otus.otuskotlin.mappers.toTransportSubscription

class SubscriptionConsumerStrategy : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicSubscriptionIn, config.kafkaTopicSubscriptionOut)
    }

    override fun serialize(source: Context): String {
        val response: SubscriptionResponse = source.toTransportSubscription()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: Context) {
        val request: SubscriptionRequest = apiV1RequestSubscriptionDeserialize(value)
        target.fromSubscriptionTransport(request)
    }
}