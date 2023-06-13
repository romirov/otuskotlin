package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.repo.DbPaymentFilterRequest
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoSearchPayment(title: String) = worker {
    this.title = title
    description = "Поиск платежей в БД по фильтру"
    on { state == State.RUNNING }
    handle {
        val request = DbPaymentFilterRequest(
            titleFilter = paymentFilterValidated.searchString,
            status = paymentFilterValidated.paymentStatus,
        )
        val result = paymentRepo.statusPayment(request)
        val resultPayments = result.data
        if (result.isSuccess && resultPayments != null) {
            paymentsRepoDone = resultPayments.toMutableList()
        } else {
            state = State.FAILING
            errors.addAll(result.errors)
        }
    }
}