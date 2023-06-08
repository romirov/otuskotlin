package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.Payment

class DbPaymentResponse (
    override val data: Payment?,
    override val isSuccess: Boolean,
    override val errors: List<CommonError> = emptyList()
): IDbResponse<Payment> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbPaymentResponse(null, true)
        fun success(result: Payment) = DbPaymentResponse(result, true)
        fun error(errors: List<CommonError>) = DbPaymentResponse(null, false, errors)
        fun error(error: CommonError) = DbPaymentResponse(null, false, listOf(error))
    }
}