package ru.otus.otuskotlin.biz.workers

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.DB_ERROR && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            CommonError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}