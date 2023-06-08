package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.models.DealSide

data class DbSubscriptionFilterRequest(
    val titleFilter: String = "",
    val dealSide: DealSide = DealSide.NONE,
)