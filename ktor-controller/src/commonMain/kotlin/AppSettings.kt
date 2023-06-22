package ru.otus.otuskotlin.app

import ru.otus.otuskotlin.biz.PaymentProcessor
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.CorSettings

data class AppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: CorSettings,
    val subscriptionProcessor: SubscriptionProcessor = SubscriptionProcessor(settings = corSettings),
    val paymentProcessor: PaymentProcessor = PaymentProcessor(settings = corSettings),
)