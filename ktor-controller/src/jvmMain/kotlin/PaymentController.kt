package ru.otus.otuskotlin.app

import io.ktor.server.application.*
import models.Command
import org.otus.otuskotlin.api.v1.models.PaymentCreateRequest
import org.otus.otuskotlin.api.v1.models.PaymentCreateResponse
import org.otus.otuskotlin.api.v1.models.PaymentStatusRequest
import org.otus.otuskotlin.api.v1.models.PaymentStatusResponse
import ru.otus.otuskotlin.logging.common.SLogWrapper

suspend fun ApplicationCall.createPayment(appSettings: SAppSettings, logger: SLogWrapper) =
processPayment<PaymentCreateRequest, PaymentCreateResponse>(appSettings, logger, "payment-create", Command.CREATE)

suspend fun ApplicationCall.statusPayment(appSettings: SAppSettings, logger: SLogWrapper) =
    processPayment<PaymentStatusRequest, PaymentStatusResponse>(appSettings, logger, "payment-status", Command.STATUS)