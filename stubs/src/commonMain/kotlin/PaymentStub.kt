package ru.otus.otuskotlin.stubs

import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.*

object SomePaymentStub {
    val PAYMENT_DEMAND_SOME: Payment = Payment(
        id = PaymentRequestId("1"),
        subscriptionId = SubscriptionRequestId("1"),
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

    fun prepareStatusList(filter: String, status: PaymentStatus) = listOf(
        PaymentStub.getPaymentStatus (get(), "d-1-01", filter, status),
        PaymentStub.getPaymentStatus(get(), "d-1-02", filter, status),
        PaymentStub.getPaymentStatus(get(), "d-1-03", filter, status),
    )

    private fun getPaymentStatus(base: Payment, id: String, filter: String, status: PaymentStatus) = base.copy(
        id = PaymentRequestId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        paymentStatus = status
    )
}