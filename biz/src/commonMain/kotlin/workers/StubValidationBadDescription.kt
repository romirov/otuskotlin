package ru.otus.otuskotlin.biz.workers

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    on { stubCase == Stubs.BAD_DESCRIPTION && state == State.RUNNING }
    handle {
        state = State.FAILING
        this.errors.add(
            CommonError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}