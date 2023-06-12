package ru.otus.otuskotlin.common

import ru.otus.otuskotlin.common.repo.IPaymentRepository
import ru.otus.otuskotlin.common.repo.ISubscriptionRepository
import ru.otus.otuskotlin.lib.logging.common.SLoggerProvider

data class CorSettings(
    val loggerProvider: SLoggerProvider = SLoggerProvider(),
    val repoSubscriptionStub: ISubscriptionRepository = ISubscriptionRepository.NONE,
    val repoSubscriptionTest: ISubscriptionRepository = ISubscriptionRepository.NONE,
    val repoSubscriptionProd: ISubscriptionRepository = ISubscriptionRepository.NONE,
    val repoPaymentStub: IPaymentRepository = IPaymentRepository.NONE,
    val repoPaymentTest: IPaymentRepository = IPaymentRepository.NONE,
    val repoPaymentProd: IPaymentRepository = IPaymentRepository.NONE,
) {
    companion object {
        val NONE = CorSettings()
    }
}