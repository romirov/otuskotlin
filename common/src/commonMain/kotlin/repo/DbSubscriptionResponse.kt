package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.CommonError

data class DbSubscriptionResponse(
    override val data: Subscription?,
    override val isSuccess: Boolean,
    override val errors: List<CommonError> = emptyList()
): IDbResponse<Subscription> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbSubscriptionResponse(null, true)
        fun success(result: Subscription) = DbSubscriptionResponse(result, true)
        fun error(errors: List<CommonError>) = DbSubscriptionResponse(null, false, errors)
        fun error(error: CommonError) = DbSubscriptionResponse(null, false, listOf(error))
    }
}