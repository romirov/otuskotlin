package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.repo.DbSubscriptionIdRequest
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoReadSubscription(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == State.RUNNING }
    handle {
        val request = DbSubscriptionIdRequest(subscriptionValidated)
        val result = subscriptionRepo.readSubscription(request)
        val resultSubscription = result.data
        if (result.isSuccess && resultSubscription != null) {
            subscriptionRepoRead = resultSubscription
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}