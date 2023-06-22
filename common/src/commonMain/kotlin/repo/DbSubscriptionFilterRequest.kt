package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.models.CommonDealSide

data class DbSubscriptionFilterRequest(
    val titleFilter: String = "",
    val commonDealSide: CommonDealSide = CommonDealSide.NONE,

    )