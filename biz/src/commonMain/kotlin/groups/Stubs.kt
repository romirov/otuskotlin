package ru.otus.otuskotlin.biz.groups

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.WorkMode
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.chain

fun ICorChainDsl<Context>.stubs(title: String, block: ICorChainDsl<Context>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == WorkMode.STUB && state == State.RUNNING }
}