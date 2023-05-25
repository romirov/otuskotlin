package ru.otus.otuskotlin.stubs

import ru.otus.otuskotlin.common.models.*

object SomePaymentStub {
    val PAYMENT_DEMAND_SOME: Payment = Payment(
        id = PaymentRequestId("1"),
        subscriptionID = SubscriptionRequestId("1"),
        title = "",
        description = "",
        price = 1.0.toBigDecimal(),
        ownerId = UserId("1"),
        paymentStatus = PaymentStatus.PASSED
    )
}

object PaymentStub {
    fun get() = SomePaymentStub.PAYMENT_DEMAND_SOME.copy()

    fun prepareResult(block: Payment.() -> Unit): Payment = get().apply(block)
}