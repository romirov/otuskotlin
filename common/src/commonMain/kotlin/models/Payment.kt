package ru.otus.otuskotlin.common.models

import java.math.BigDecimal

data class Payment(
    var id: PaymentRequestId = PaymentRequestId.NONE,
    var subscriptionId: SubscriptionRequestId = SubscriptionRequestId.NONE,
    var title: String = "",
    var description: String = "",
    var price: BigDecimal = 0.0.toBigDecimal(),
    var ownerId: UserId = UserId.NONE,
    var paymentStatus: PaymentStatus = PaymentStatus.NONE
){
    fun deepCopy(): Payment = copy()
}