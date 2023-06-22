package ru.otus.otuskotlin.common.models

data class SubscriptionFilter(
    var searchString: String = "",
    var subscriptionStatus: SubscriptionStatus = SubscriptionStatus.NONE,
    var commonDealSide: CommonDealSide = CommonDealSide.NONE
)