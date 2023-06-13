package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.repo.DbSubscriptionIdRequest
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoDeleteSubscription(title: String) = worker {
    this.title = title
    description = "Удаление подписки из БД по ID"
    on { state == State.RUNNING }
    handle {
        val request = DbSubscriptionIdRequest(subscriptionRepoPrepare)
        val result = subscriptionRepo.deleteSubscription(request)
        if (!result.isSuccess) {
            state = State.FAILING
            errors.addAll(result.errors)
        }
        subscriptionRepoDone = subscriptionRepoRead
    }
}