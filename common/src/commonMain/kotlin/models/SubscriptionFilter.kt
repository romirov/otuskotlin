package ru.otus.otuskotlin.common.models

data class SubscriptionFilter(
    var searchString: String = "",
    var subscriptionStatus: SubscriptionStatus = SubscriptionStatus.NONE,
    var dealSide: DealSide = DealSide.NONE
)