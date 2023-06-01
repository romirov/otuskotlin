package ru.otus.otuskotlin.biz.workers

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker
import ru.otus.otuskotlin.stubs.PaymentStub
import ru.otus.otuskotlin.stubs.SubscriptionStub

fun ICorChainDsl<Context>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stubSubscrition = SubscriptionStub.prepareResult {
            subscriptionRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        subscriptionResponse = stubSubscrition


        val stubPayment = PaymentStub.prepareResult {
            paymentRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        paymentResponse = stubPayment
    }
}
