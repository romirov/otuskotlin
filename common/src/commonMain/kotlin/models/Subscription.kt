package ru.otus.otuskotlin.common

import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.ProductId
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.models.SubscriptionStatus

data class Subscription(
    var id: SubscriptionRequestId = SubscriptionRequestId.NONE,
    var title: String = "",
    var description: String = "",
    var productId: ProductId = ProductId.NONE,
    val subscriptionStatus: SubscriptionStatus = SubscriptionStatus.NONE,
    val subscriptionType: DealSide = DealSide.NONE
)