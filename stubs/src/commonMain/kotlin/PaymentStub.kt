import models.*

object SomePaymentStub {
    val PAYMENT_DEMAND_SOME: Payment = Payment(
        id = PaymentId("1"),
        subscriptionID = SubscriptionId("1"),
        title = "",
        description = "",
        price = 1.0,
        ownerId = UserId("1"),
        paymentStatus = PaymentStatus.PASSED
    )
}

object PaymentStub {
    fun get() = SomePaymentStub.PAYMENT_DEMAND_SOME.copy()

    fun prepareResult(block: Payment.() -> Unit): Payment = get().apply(block)
}