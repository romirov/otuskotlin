package models

data class Subscription(
    var id: SubscriptionId = SubscriptionId.NONE,
    var title: String = "",
    var description: String = "",
    var productId: ProductId = ProductId.NONE,
    val subscriptionStatus: SubscriptionStatus = SubscriptionStatus.NONE,
    val subscriptionType: DealSide = DealSide.NONE
)