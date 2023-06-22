package ru.otus.otuskotlin.repo.inmemory.model

import ru.otus.otuskotlin.common.*
import ru.otus.otuskotlin.common.models.CommonDealSide
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.models.SubscriptionStatus

data class SubscriptionEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val subscriptionStatus: String? = null,
    val subscriptionType: String? = null,
) {
    constructor(model: Subscription): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        subscriptionStatus = model.subscriptionStatus.takeIf { it != SubscriptionStatus.NONE }?.name,
        subscriptionType = model.subscriptionType.takeIf { it != CommonDealSide.NONE }?.name,
    )

    fun toInternal() = Subscription(
        id = id?.let { SubscriptionRequestId(it) }?: SubscriptionRequestId.NONE,
        title = title?: "",
        description = description?: "",
        subscriptionStatus = subscriptionStatus?.let { SubscriptionStatus.valueOf(it) }?: SubscriptionStatus.NONE,
        subscriptionType = subscriptionType?.let { CommonDealSide.valueOf(it) }?: CommonDealSide.NONE,
    )
}
