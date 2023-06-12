package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.SubscriptionStatus

data class DbSubscriptionFilterStatusRequest(
    val titleFilter: String = "",
    val status: SubscriptionStatus = SubscriptionStatus.NONE,
)