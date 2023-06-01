package ru.otus.otuskotlin.biz.workers

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.helpers.fail
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == State.RUNNING }
    handle {
        fail(
            CommonError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}