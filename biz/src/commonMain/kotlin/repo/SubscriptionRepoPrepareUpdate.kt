package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoPrepareUpdateSubscription(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == State.RUNNING }
    handle {
        subscriptionRepoPrepare = subscriptionRepoRead.deepCopy().apply {
            this.title = subscriptionValidated.title
            description = subscriptionValidated.description
            subscriptionType = subscriptionValidated.subscriptionType
        }
    }
}