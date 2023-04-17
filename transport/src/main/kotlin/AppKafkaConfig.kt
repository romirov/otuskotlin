package ru.otus.otuskotlin.transport

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicSubscriptionIn: String = KAFKA_TOPIC_SUBSCRIPTION_IN,
    val kafkaTopicSubscriptionOut: String = KAFKA_TOPIC_SUBSCRIPTION_OUT,
    val kafkaTopicPaymentIn: String = KAFKA_TOPIC_PAYMENT_IN,
    val kafkaTopicPaymentOut: String = KAFKA_TOPIC_PAYMENT_OUT,
) {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"
        const val KAFKA_TOPIC_SUBSCRIPTION_IN_VAR = "KAFKA_TOPIC_SUBSCRIPTION_IN"
        const val KAFKA_TOPIC_SUBSCRIPTION_OUT_VAR = "KAFKA_TOPIC_SUBSCRIPTION_OUT"
        const val KAFKA_TOPIC_PAYMENT_IN_VAR = "KAFKA_TOPIC_PAYMENT_IN"
        const val KAFKA_TOPIC_PAYMENT_OUT_VAR = "KAFKA_TOPIC_PAYMENT_OUT"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,;]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "subscription" }
        val KAFKA_TOPIC_SUBSCRIPTION_IN by lazy { System.getenv(KAFKA_TOPIC_SUBSCRIPTION_IN_VAR) ?: "subscription-in" }
        val KAFKA_TOPIC_SUBSCRIPTION_OUT by lazy { System.getenv(KAFKA_TOPIC_SUBSCRIPTION_OUT_VAR) ?: "subscription-out" }
        val KAFKA_TOPIC_PAYMENT_IN by lazy { System.getenv(KAFKA_TOPIC_PAYMENT_IN_VAR) ?: "payment-in" }
        val KAFKA_TOPIC_PAYMENT_OUT by lazy { System.getenv(KAFKA_TOPIC_PAYMENT_OUT_VAR) ?: "payment-out" }
    }
}
