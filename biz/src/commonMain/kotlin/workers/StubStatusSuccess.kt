package ru.otus.otuskotlin.biz.workers

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.PaymentStatus
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker
import ru.otus.otuskotlin.stubs.PaymentStub

fun ICorChainDsl<Context>.stubStatusSuccess(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stubPayment = PaymentStub.prepareResult {
            paymentRequest.paymentStatus.takeIf { it != PaymentStatus.NONE }?.also { this.paymentStatus = it }
        }
        paymentResponse = stubPayment
    }
}