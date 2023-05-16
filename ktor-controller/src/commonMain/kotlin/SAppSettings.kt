package ru.otus.otuskotlin.app

import CorSettings
import ru.otus.otuskotlin.biz.PaymentProcessor
import ru.otus.otuskotlin.biz.SubscriptionProcessor

data class SAppSettings(
    val appUrls: List<String>,
    val corSettings: CorSettings,
    val subscriptionProcessor: SubscriptionProcessor,
    val paymentProcessor: PaymentProcessor,
)