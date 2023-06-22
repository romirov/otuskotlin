import ru.otus.otuskotlin.common.models.PaymentStatus
import ru.otus.otuskotlin.common.repo.*
import ru.otus.otuskotlin.stubs.PaymentStub

class PaymentRepoStub : IPaymentRepository {
    override suspend fun createPayment(rq: DbPaymentRequest): DbPaymentResponse {
        return DbPaymentResponse(
            data = PaymentStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun statusPayment(rq: DbPaymentFilterRequest): DbPaymentsResponse {
        return DbPaymentsResponse(
            data = PaymentStub.prepareStatusList(filter = "", PaymentStatus.PASSED),
            isSuccess = true,
        )
    }
}