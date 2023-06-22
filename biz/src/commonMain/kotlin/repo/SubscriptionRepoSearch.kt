package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.repo.DbSubscriptionFilterRequest
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoSearchSubscription(title: String) = worker {
    this.title = title
    description = "Поиск подписок в БД по фильтру"
    on { state == State.RUNNING }
    handle {
        val request = DbSubscriptionFilterRequest(
            titleFilter = subscriptionFilterValidated.searchString,
            commonDealSide = subscriptionFilterValidated.commonDealSide,
        )
        val result = subscriptionRepo.searchSubscription(request)
        val resultSubscriptions = result.data
        if (result.isSuccess && resultSubscriptions != null) {
            subscriptionsRepoDone = resultSubscriptions.toMutableList()
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}