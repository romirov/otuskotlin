package ru.otus.otuskotlin.common.models

data class PaymentFilter(
    var searchString: String = "",
    var paymentStatus: PaymentStatus = PaymentStatus.NONE,
)