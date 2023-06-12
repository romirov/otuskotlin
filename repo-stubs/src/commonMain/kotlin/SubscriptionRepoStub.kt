import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.SubscriptionStatus
import ru.otus.otuskotlin.common.repo.*
import ru.otus.otuskotlin.stubs.SubscriptionStub

class SubscriptionRepoStub : ISubscriptionRepository {
    override suspend fun createSubscription(rq: DbSubscriptionRequest): DbSubscriptionResponse {
        return DbSubscriptionResponse(
            data = SubscriptionStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readSubscription(rq: DbSubscriptionIdRequest): DbSubscriptionResponse {
        return DbSubscriptionResponse(
            data = SubscriptionStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateSubscription(rq: DbSubscriptionRequest): DbSubscriptionResponse {
        return DbSubscriptionResponse(
            data = SubscriptionStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteSubscription(rq: DbSubscriptionIdRequest): DbSubscriptionResponse {
        return DbSubscriptionResponse(
            data = SubscriptionStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchSubscription(rq: DbSubscriptionFilterRequest): DbSubscriptionsResponse {
        return DbSubscriptionsResponse(
            data = SubscriptionStub.prepareSearchList(filter = "", DealSide.DEMAND),
            isSuccess = true,
        )
    }

    override suspend fun statusSubscription(rq: DbSubscriptionFilterStatusRequest): DbSubscriptionsResponse {
        return DbSubscriptionsResponse(
            data = SubscriptionStub.prepareSearchStatusList(filter = "", SubscriptionStatus.ACTIVE),
            isSuccess = true,
        )
    }
}
