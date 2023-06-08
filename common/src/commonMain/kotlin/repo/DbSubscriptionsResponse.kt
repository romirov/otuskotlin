package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.CommonError

class DbSubscriptionsResponse(
    override val data: List<Subscription>?,
    override val isSuccess: Boolean,
    override val errors: List<CommonError> = emptyList(),
) : IDbResponse<List<Subscription>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbSubscriptionsResponse(emptyList(), true)
        fun success(result: List<Subscription>) = DbSubscriptionsResponse(result, true)
        fun error(errors: List<CommonError>) = DbSubscriptionsResponse(null, false, errors)
        fun error(error: CommonError) = DbSubscriptionsResponse(null, false, listOf(error))
    }
}
