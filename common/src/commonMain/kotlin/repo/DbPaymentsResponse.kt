package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.Payment

class DbPaymentsResponse  (
    override val data: List<Payment>?,
    override val isSuccess: Boolean,
    override val errors: List<CommonError> = emptyList(),
): IDbResponse<List<Payment>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbPaymentsResponse(emptyList(), true)
        fun success(result: List<Payment>) = DbPaymentsResponse(result, true)
        fun error(errors: List<CommonError>) = DbPaymentsResponse(null, false, errors)
        fun error(error: CommonError) = DbPaymentsResponse(null, false, listOf(error))
    }
}