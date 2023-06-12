package ru.otus.otuskotlin.repo.tests

import ru.otus.otuskotlin.common.repo.*

class SubscriptionRepositoryMock(
    private val invokeCreateSubscription: (DbSubscriptionRequest) -> DbSubscriptionResponse = { DbSubscriptionResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadSubscription: (DbSubscriptionIdRequest) -> DbSubscriptionResponse = { DbSubscriptionResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateSubscription: (DbSubscriptionRequest) -> DbSubscriptionResponse = { DbSubscriptionResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteSubscription: (DbSubscriptionIdRequest) -> DbSubscriptionResponse = { DbSubscriptionResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchSubscription: (DbSubscriptionFilterRequest) -> DbSubscriptionsResponse = { DbSubscriptionsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeStatusSubscription: (DbSubscriptionFilterStatusRequest) -> DbSubscriptionsResponse = { DbSubscriptionsResponse.MOCK_SUCCESS_EMPTY },
) : ISubscriptionRepository {
    override suspend fun createSubscription(rq: DbSubscriptionRequest): DbSubscriptionResponse {
        return invokeCreateSubscription(rq)
    }

    override suspend fun readSubscription(rq: DbSubscriptionIdRequest): DbSubscriptionResponse {
        return invokeReadSubscription(rq)
    }

    override suspend fun updateSubscription(rq: DbSubscriptionRequest): DbSubscriptionResponse {
        return invokeUpdateSubscription(rq)
    }

    override suspend fun deleteSubscription(rq: DbSubscriptionIdRequest): DbSubscriptionResponse {
        return invokeDeleteSubscription(rq)
    }

    override suspend fun searchSubscription(rq: DbSubscriptionFilterRequest): DbSubscriptionsResponse {
        return invokeSearchSubscription(rq)
    }

    override suspend fun statusSubscription(rq: DbSubscriptionFilterStatusRequest): DbSubscriptionsResponse {
        return invokeStatusSubscription(rq)
    }
}