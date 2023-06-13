package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.repo.DbSubscriptionRequest
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoCreateSubscription(title: String) = worker {
    this.title = title
    description = "Добавление подписки в БД"
    on { state == State.RUNNING }
    handle {
        val request = DbSubscriptionRequest(subscriptionRepoPrepare)
        val result = subscriptionRepo.createSubscription(request)
        val resultSubscription = result.data
        if (result.isSuccess && resultSubscription != null) {
            subscriptionRepoDone = resultSubscription
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}