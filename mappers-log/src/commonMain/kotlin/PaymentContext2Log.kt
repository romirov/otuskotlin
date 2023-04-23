import kotlinx.datetime.Clock
import models.*
import org.otus.otuskotlin.api.logs.models.CommonPaymentLogModel
import org.otus.otuskotlin.api.logs.models.PaymentErrorLogModel
import org.otus.otuskotlin.api.logs.models.PaymentLog
import org.otus.otuskotlin.api.logs.models.PaymentLogModel
import java.math.BigDecimal

fun Context.toPaymentLog(logId: String) = CommonPaymentLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "otuskotlin",
    payment = toPaymentLog(),
    errors = errors.map { it.toPaymentLog() },
)

fun Context.toPaymentLog(): PaymentLogModel? {
    val paymentNone = Payment()
    return PaymentLogModel(
        requestId = requestId.takeIf { it != RequestId.NONE }?.asString(),
        requestPayment = paymentRequest.takeIf { it != paymentNone }?.toPaymentLog(),
        responsePayment = paymentResponse.takeIf { it != paymentNone }?.toPaymentLog(),
        responsePayments = paymentsResponse.takeIf { it.isNotEmpty() }?.filter { it != paymentNone }
            ?.map { it.toPaymentLog() },
    ).takeIf { it != PaymentLogModel() }
}

fun CommonError.toPaymentLog() = PaymentErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun Payment.toPaymentLog() = PaymentLog(
    id = id.takeIf { it != PaymentId.NONE }?.asString(),
    subscriptionId = id.takeIf { it != PaymentId.NONE }.toString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    price = price.takeIf { it != BigDecimal.ZERO }.toString(),
    paymentStatus = paymentStatus.takeIf { it != PaymentStatus.NONE }?.name,
    ownerId = ownerId.takeIf { it != UserId.NONE }?.asString(),
)
