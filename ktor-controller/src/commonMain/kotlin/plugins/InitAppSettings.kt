package ru.otus.otuskotlin.app.plugins

import PaymentRepoStub
import SubscriptionRepoStub
import io.ktor.server.application.*
import ru.otus.otuskotlin.app.AppSettings
import ru.otus.otuskotlin.biz.PaymentProcessor
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.lib.logging.common.SLoggerProvider
import ru.otus.otuskotlin.repo.inmemory.PaymentRepoInMemory
import ru.otus.otuskotlin.repo.inmemory.SubscriptionRepoInMemory

fun Application.initAppSettings(): AppSettings {
    val corSettings = CorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoSubscriptionTest = SubscriptionRepoInMemory(),
        repoSubscriptionProd = SubscriptionRepoInMemory(),
        repoSubscriptionStub = SubscriptionRepoStub(),
        repoPaymentTest = PaymentRepoInMemory(),
        repoPaymentProd = PaymentRepoInMemory(),
        repoPaymentStub = PaymentRepoStub(),

    )
    return AppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        subscriptionProcessor = SubscriptionProcessor(corSettings),
        paymentProcessor = PaymentProcessor(corSettings),
    )
}

expect fun Application.getLoggerProviderConf(): SLoggerProvider