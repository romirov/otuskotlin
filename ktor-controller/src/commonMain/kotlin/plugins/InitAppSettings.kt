package ru.otus.otuskotlin.app.plugins

import CorSettings
import io.ktor.server.application.*
import ru.otus.otuskotlin.app.SAppSettings
import ru.otus.otuskotlin.biz.PaymentProcessor
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.logging.common.SLoggerProvider

fun Application.initAppSettings(): SAppSettings = SAppSettings(
    appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
    corSettings = CorSettings(
        loggerProvider = getLoggerProviderConf()
    ),
    subscriptionProcessor = SubscriptionProcessor(),
    paymentProcessor = PaymentProcessor(),
)

expect fun Application.getLoggerProviderConf(): SLoggerProvider