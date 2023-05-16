package ru.otus.otuskotlin.mappers

import Context
import models.Command
import models.CommonError
import models.State
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.mappers.exceptions.UnknownSubscriptionCommand

fun Context.toTransportPayment(): PaymentResponse = when (val cmd = command) {
    Command.CREATE -> toTransportPaymentCreate()
    Command.STATUS -> toTransportPaymentStatus()
    else -> throw UnknownSubscriptionCommand(cmd)
}

fun Context.toTransportPaymentCreate() = PaymentCreateResponse(
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

fun Context.toTransportPaymentStatus() = PaymentStatusResponse(
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == models.State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors()
)

private fun List<CommonError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportPayment() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CommonError.toTransportPayment() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
