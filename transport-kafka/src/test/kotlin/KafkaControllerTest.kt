package ru.otus.otuskotlin.transport.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.api.apiV1RequestSerialize
import ru.otus.otuskotlin.api.apiV1ResponseSubscriptionDeserialize
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicSubscriptionIn
        val outputTopic = config.kafkaTopicSubscriptionOut

        val app = AppKafkaSubscriptionConsumer(config, listOf(SubscriptionConsumerStrategy()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        SubscriptionCreateRequest(
                        requestId = "11111111-1111-1111-1111-111111111111",
                        subscription = SubscriptionCreateObject(
                            title = "Some subscription",
                            description = "some testing subscription to check them all",
                            subscriptionType = DealSide.DEMAND,
                            status = SubscriptionStatus.INACTIVE
                        ),
                        debug = SubscriptionDebug(
                            mode = SubscriptionRequestDebugMode.STUB,
                            stub = SubscriptionRequestDebugStubs.SUCCESS
                        )
                    )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiV1ResponseSubscriptionDeserialize<SubscriptionCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("11111111-1111-1111-1111-111111111111", result.requestId)
        assertEquals("PostgreSQL cloud service", result.subscription?.title)
    }

    companion object {
        const val PARTITION = 0
    }
}

