package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.SubscriptionRequestId

data class DbSubscriptionIdRequest(
    val id: SubscriptionRequestId,
) {
    constructor(ad: Subscription): this(ad.id)
}