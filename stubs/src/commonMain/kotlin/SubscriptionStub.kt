package ru.otus.otuskotlin.stubs

import models.*
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.ProductId
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.models.SubscriptionStatus

object SomeSubscriptionStub {
    val SUBSCRIPTION_DEMAND: Subscription
        get() = Subscription(
            id = SubscriptionRequestId("1"),
            title = "PostgreSQL cloud service",
            description = "PostgreSQL cloud database service",
            productId = ProductId("1"),
            subscriptionStatus = SubscriptionStatus.INACTIVE,
            subscriptionType = DealSide.DEMAND,
        )
    val SUBSCRIPTION_SUPPLY = SUBSCRIPTION_DEMAND.copy(subscriptionType = DealSide.SUPPLY)
}

object SubscriptionStub {
    fun get() = SomeSubscriptionStub.SUBSCRIPTION_DEMAND.copy()

    fun prepareResult(block: Subscription.() -> Unit): Subscription = get().apply(block)

    fun prepareSearchList(filter: String, type: DealSide) = listOf(
        getSubscriptionDemand("d-1-01", filter, type),
        getSubscriptionDemand("d-1-02", filter, type),
        getSubscriptionDemand("d-1-03", filter, type),
        getSubscriptionDemand("d-1-04", filter, type),
        getSubscriptionDemand("d-1-05", filter, type),
        getSubscriptionDemand("d-1-06", filter, type),
    )

    fun prepareOffersList(filter: String, type: DealSide) = listOf(
        getSubscriptionSupply("s-1-01", filter, type),
        getSubscriptionSupply("s-1-02", filter, type),
        getSubscriptionSupply("s-1-03", filter, type),
        getSubscriptionSupply("s-1-04", filter, type),
        getSubscriptionSupply("s-1-05", filter, type),
        getSubscriptionSupply("s-1-06", filter, type),
    )

    private fun getSubscriptionDemand(id: String, filter: String, type: DealSide) =
        getSubscription(SomeSubscriptionStub.SUBSCRIPTION_DEMAND, id = id, filter = filter, type = type)

    private fun getSubscriptionSupply(id: String, filter: String, type: DealSide) =
        getSubscription(SomeSubscriptionStub.SUBSCRIPTION_SUPPLY, id = id, filter = filter, type = type)

    private fun getSubscription(base: Subscription, id: String, filter: String, type: DealSide) = base.copy(
        id = SubscriptionRequestId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        subscriptionType = type
    )
}