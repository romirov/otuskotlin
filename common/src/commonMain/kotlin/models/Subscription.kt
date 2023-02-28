package models

data class Subscription(
    var id: SubscriptionId = SubscriptionId.NONE,
    var title: String = "",
    var description: String = "",
    var productId: ProductId = ProductId.NONE,
    var ownerId: UserId = UserId.NONE
)