package models

data class Filter(
    var searchString: String = "",
    var statusString: SubscriptionStatus = SubscriptionStatus.NONE,
    var dealSide: DealSide = DealSide.NONE
)