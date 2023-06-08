package ru.otus.otuskotlin.common.repo

interface IPaymentRepository {
    suspend fun createPayment(rq: DbPaymentRequest): DbPaymentResponse
    suspend fun statusPayment(rq: DbPaymentFilterRequest): DbPaymentsResponse
    companion object {
        val NONE = object : IPaymentRepository {
            override suspend fun createPayment(rq: DbPaymentRequest): DbPaymentResponse {
                TODO("Not yet implemented")
            }

            override suspend fun statusPayment(rq: DbPaymentFilterRequest): DbPaymentsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}