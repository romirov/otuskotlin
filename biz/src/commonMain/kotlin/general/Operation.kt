package ru.otus.otuskotlin.biz.groups

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.chain

fun ICorChainDsl<Context>.operation(title: String, command: Command, block: ICorChainDsl<Context>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == State.RUNNING }
}