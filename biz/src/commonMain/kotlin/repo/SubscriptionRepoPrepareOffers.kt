package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoPrepareOffersSubscription(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску предложений в БД"
    on { state == State.RUNNING }
    handle {
        subscriptionRepoPrepare = subscriptionRepoRead.deepCopy()
        subscriptionRepoDone = subscriptionRepoRead.deepCopy()
    }
}