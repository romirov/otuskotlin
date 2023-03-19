package models

data class Payment(
    var id: PaymentId = PaymentId.NONE,
    var subscriptionID: SubscriptionId = SubscriptionId.NONE,
    var description: String = "",
    var title: String = "",
    var price: Double = 0.0,
    var ownerId: UserId = UserId.NONE
)