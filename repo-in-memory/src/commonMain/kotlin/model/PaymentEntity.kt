package ru.otus.otuskotlin.repo.inmemory.model

import ru.otus.otuskotlin.common.models.Payment
import ru.otus.otuskotlin.common.models.PaymentRequestId
import ru.otus.otuskotlin.common.models.PaymentStatus
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import java.math.BigDecimal

data class PaymentEntity(
    val id: String? = null,
    val subscriptionId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: String? = null,
    val paymentStatus: String? = null,
) {
    constructor(model: Payment) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        subscriptionId = model.subscriptionId.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        price = model.price.takeIf { it > BigDecimal.ZERO }?.toString(),
        paymentStatus = model.paymentStatus.takeIf { it != PaymentStatus.NONE }?.name,
    )

    fun toInternal() = Payment(
        id = id?.let { PaymentRequestId(it) } ?: PaymentRequestId.NONE,
        subscriptionId = id?.let { SubscriptionRequestId(it) } ?: SubscriptionRequestId.NONE,
        title = title ?: "",
        description = description ?: "",
        price = price?.toBigDecimal() ?: BigDecimal.ZERO,
        paymentStatus = paymentStatus?.let { PaymentStatus.valueOf(it) } ?: PaymentStatus.NONE,
    )
}
