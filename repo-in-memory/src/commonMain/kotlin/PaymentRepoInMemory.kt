package ru.otus.otuskotlin.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.Payment
import ru.otus.otuskotlin.common.models.PaymentRequestId
import ru.otus.otuskotlin.common.models.PaymentStatus
import ru.otus.otuskotlin.common.repo.*
import ru.otus.otuskotlin.repo.inmemory.model.PaymentEntity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class PaymentRepoInMemory (
    initObjects: List<Payment> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IPaymentRepository {

    private val cache = Cache.Builder<String, PaymentEntity>()
        .expireAfterWrite(ttl)
        .build()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(payment: Payment) {
        val entity = PaymentEntity(payment)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createPayment(rq: DbPaymentRequest): DbPaymentResponse {
        val key = randomUuid()
        val ad = rq.payment.copy(id = PaymentRequestId(key))
        val entity = PaymentEntity(ad)
        cache.put(key, entity)
        return DbPaymentResponse(
            data = ad,
            isSuccess = true,
        )
    }

    override suspend fun statusPayment(rq: DbPaymentFilterRequest): DbPaymentsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.status.takeIf { it != PaymentStatus.NONE }?.let {
                    it.name == entry.value.paymentStatus
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbPaymentsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbPaymentResponse(
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
        val resultErrorNotFound = DbPaymentResponse(
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