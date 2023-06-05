package ru.otus.otuskotlin.biz.validation

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.chain

fun ICorChainDsl<Context>.validation(block: ICorChainDsl<Context>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == State.RUNNING }
}
