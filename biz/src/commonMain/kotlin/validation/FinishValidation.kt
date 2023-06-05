package ru.otus.otuskotlin.biz.validation

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        subscriptionValidated = subscriptionValidating
    }
}

fun ICorChainDsl<Context>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        subscriptionFilterValidated = subscriptionFilterValidating
    }
}
