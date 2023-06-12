package ru.otus.otuskotlin.repo.tests

import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.SubscriptionRequestId

abstract class BaseInitSubscriptions(val op: String) : IInitObjects<Subscription> {

    fun createInitTestModel(
        suf: String,
        subscriptionType: DealSide = DealSide.DEMAND,
    ) = Subscription(
        id = SubscriptionRequestId("subscription-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        subscriptionType = subscriptionType,
    )
}
