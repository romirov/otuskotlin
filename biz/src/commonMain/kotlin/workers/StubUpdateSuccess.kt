package ru.otus.otuskotlin.biz.workers

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.CommonDealSide
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker
import ru.otus.otuskotlin.stubs.SubscriptionStub

fun ICorChainDsl<Context>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.SUCCESS && state == State.RUNNING }
    handle {
        state = State.FINISHING
        val stub = SubscriptionStub.prepareResult {
            subscriptionRequest.id.takeIf { it != SubscriptionRequestId.NONE }?.also { this.id = it }
            subscriptionRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            subscriptionRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            subscriptionRequest.subscriptionType.takeIf { it != CommonDealSide.NONE }?.also { this.subscriptionType = it }
        }
        subscriptionResponse = stub
    }
}