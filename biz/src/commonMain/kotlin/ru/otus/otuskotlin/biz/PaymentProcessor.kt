package ru.otus.otuskotlin.biz

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.stubs.PaymentStub


class PaymentProcessor {
    suspend fun exec(ctx: Context) {
        ctx.paymentResponse = PaymentStub.get()
    }
}