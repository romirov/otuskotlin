package ru.otus.otuskotlin.biz

import Context
import Payment

class PaymentProcessor {
    suspend fun exec(ctx: Context) {
        ctx.paymentResponse = PaymentStub.get()
    }
}