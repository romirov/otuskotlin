package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.models.PaymentStatus

class DbPaymentFilterRequest(
    val titleFilter: String = "",
    val status: PaymentStatus = PaymentStatus.NONE,
)