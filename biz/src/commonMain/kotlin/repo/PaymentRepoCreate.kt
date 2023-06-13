package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.repo.DbPaymentRequest
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoCreatePayment(title: String) = worker {
    this.title = title
    description = "Добавление платежа в БД"
    on { state == State.RUNNING }
    handle {
        val request = DbPaymentRequest(paymentRepoPrepare)
        val result = paymentRepo.createPayment(request)
        val resultPayment = result.data
        if (result.isSuccess && resultPayment != null) {
            paymentRepoDone = resultPayment
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}