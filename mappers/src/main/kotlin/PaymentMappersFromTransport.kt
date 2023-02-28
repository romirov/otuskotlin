package ru.otus.otuskotlin.mappers

import Context
import models.*
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.mappers.exceptions.UnknownRequestClass
import stubs.Stubs

fun Context.fromPaymentTransport(request: PaymentRequest) = when (request) {
    is PaymentCreateRequest -> fromPaymentTransport(request)
    is PaymentStatusRequest -> fromPaymentTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toPaymentId() = this?.let { PaymentId(it) } ?: PaymentId.NONE
private fun String?.toPaymentWithId() = Payment(id = this.toPaymentId())
private fun PaymentRequest?.requestId() = this?.requestId?.let { RequestId(it) } ?: RequestId.NONE

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
    requestId = request.requestId()
    paymentRequest = request.payment?.toInternal() ?: Payment()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromPaymentTransport(request: PaymentStatusRequest) {
    command = Command.STATUS
    requestId = request.requestId()
    paymentRequest = request.payment?.id.toPaymentWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun PaymentCreateObject.toInternal(): Payment = Payment(
    title = this.title ?: "",
)
