package ru.otus.otuskotlin.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.models.SubscriptionStatus
import ru.otus.otuskotlin.common.repo.*
import ru.otus.otuskotlin.repo.inmemory.model.SubscriptionEntity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class SubscriptionRepoInMemory (
    initObjects: List<Subscription> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : ISubscriptionRepository {

    private val cache = Cache.Builder<String, SubscriptionEntity>()
        .expireAfterWrite(ttl)
        .build()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(subscription: Subscription) {
        val entity = SubscriptionEntity(subscription)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createSubscription(rq: DbSubscriptionRequest): DbSubscriptionResponse {
        val key = randomUuid()
        val subscription = rq.subscription.copy(id = SubscriptionRequestId(key))
        val entity = SubscriptionEntity(subscription)
        cache.put(key, entity)
        return DbSubscriptionResponse(
            data = subscription,
            isSuccess = true,
        )
    }

    override suspend fun readSubscription(rq: DbSubscriptionIdRequest): DbSubscriptionResponse {
        val key = rq.id.takeIf { it != SubscriptionRequestId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbSubscriptionResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateSubscription(rq: DbSubscriptionRequest): DbSubscriptionResponse {
        val key = rq.subscription.id.takeIf { it != SubscriptionRequestId.NONE }?.asString() ?: return resultErrorEmptyId
        val newSubscription = rq.subscription.copy()
        val entity = SubscriptionEntity(newSubscription)
        return when (cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.put(key, entity)
                DbSubscriptionResponse(
                    data = newSubscription,
                    isSuccess = true,
                )
            }
        }
    }

    override suspend fun deleteSubscription(rq: DbSubscriptionIdRequest): DbSubscriptionResponse {
        val key = rq.id.takeIf { it != SubscriptionRequestId.NONE }?.asString() ?: return resultErrorEmptyId
        return when (val oldSubscription = cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.invalidate(key)
                DbSubscriptionResponse(
                    data = oldSubscription.toInternal(),
                    isSuccess = true,
                )
            }
        }
    }

    override suspend fun searchSubscription(rq: DbSubscriptionFilterRequest): DbSubscriptionsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.dealSide.takeIf { it != DealSide.NONE }?.let {
                    it.name == entry.value.subscriptionType
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbSubscriptionsResponse(
            data = result,
            isSuccess = true
        )
    }

    override suspend fun statusSubscription(rq: DbSubscriptionFilterStatusRequest): DbSubscriptionsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.status.takeIf { it != SubscriptionStatus.NONE }?.let {
                    it.name == entry.value.subscriptionStatus
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbSubscriptionsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbSubscriptionResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CommonError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbSubscriptionResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                CommonError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
