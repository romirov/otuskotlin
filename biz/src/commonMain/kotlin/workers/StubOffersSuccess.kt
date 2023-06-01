package ru.otus.otuskotlin.biz.workers

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker
import ru.otus.otuskotlin.stubs.SubscriptionStub

fun ICorChainDsl<Context>.stubOffersSuccess(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        subscriptionResponse = SubscriptionStub.prepareResult {
            subscriptionRequest.id.takeIf { it != SubscriptionRequestId.NONE }?.also { this.id = it }
        }
        subscriptionsResponse.addAll(SubscriptionStub.prepareOffersList(subscriptionResponse.title, DealSide.SUPPLY))
    }
}