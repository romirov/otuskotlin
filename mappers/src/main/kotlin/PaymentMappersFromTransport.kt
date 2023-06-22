package ru.otus.otuskotlin.mappers

import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.Payment
import ru.otus.otuskotlin.common.models.PaymentRequestId
import ru.otus.otuskotlin.common.models.WorkMode
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.mappers.exceptions.UnknownRequestClass

fun Context.fromPaymentTransport(request: PaymentRequest) = when (request) {
    is PaymentCreateRequest -> fromPaymentTransport(request)
    is PaymentStatusRequest -> fromPaymentTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toPaymentId() = this?.let { PaymentRequestId(it) } ?: PaymentRequestId.NONE
private fun String?.toPaymentWithId() = Payment(id = this.toPaymentId())
private fun PaymentRequest?.requestId() = this?.requestId?.let { PaymentRequestId(it) } ?: PaymentRequestId.NONE

private fun PaymentDebug?.transportToWorkMode(): WorkMode = when (this?.mode) {
    PaymentRequestDebugMode.PROD -> WorkMode.PROD
    PaymentRequestDebugMode.TEST -> WorkMode.TEST
    PaymentRequestDebugMode.STUB -> WorkMode.STUB
    null -> WorkMode.PROD
}

private fun PaymentDebug?.transportToStubCase(): Stubs = when (this?.stub) {
    PaymentRequestDebugStubs.SUCCESS -> Stubs.SUCCESS
    PaymentRequestDebugStubs.NOT_FOUND -> Stubs.NOT_FOUND
    PaymentRequestDebugStubs.BAD_ID -> Stubs.BAD_ID
    PaymentRequestDebugStubs.BAD_TITLE -> Stubs.BAD_TITLE
    PaymentRequestDebugStubs.BAD_DESCRIPTION -> Stubs.BAD_DESCRIPTION
    null -> Stubs.NONE
}

fun Context.fromPaymentTransport(request: PaymentCreateRequest) {
    command = Command.CREATE
    paymentRequestId = request.requestId()
    paymentRequest = request.payment?.toInternal() ?: Payment()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromPaymentTransport(request: PaymentStatusRequest) {
    command = Command.STATUS
    paymentRequestId = request.requestId()
    paymentRequest = request.payment?.id.toPaymentWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun PaymentCreateObject.toInternal(): Payment = Payment(
    title = this.title ?: "",
)
