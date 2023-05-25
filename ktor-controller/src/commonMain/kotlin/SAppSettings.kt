package ru.otus.otuskotlin.app

import ru.otus.otuskotlin.biz.PaymentProcessor
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.CorSettings

data class SAppSettings(
    val appUrls: List<String>,
    val corSettings: CorSettings,
    val subscriptionProcessor: SubscriptionProcessor,
    val paymentProcessor: PaymentProcessor,
)