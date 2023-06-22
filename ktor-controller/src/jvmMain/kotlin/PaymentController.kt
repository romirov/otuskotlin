package ru.otus.otuskotlin.app

import io.ktor.server.application.*
import org.otus.otuskotlin.api.v1.models.PaymentCreateRequest
import org.otus.otuskotlin.api.v1.models.PaymentCreateResponse
import org.otus.otuskotlin.api.v1.models.PaymentStatusRequest
import org.otus.otuskotlin.api.v1.models.PaymentStatusResponse
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.lib.logging.common.SLogWrapper

suspend fun ApplicationCall.createPayment(appSettings: AppSettings, logger: SLogWrapper) =
processPayment<PaymentCreateRequest, PaymentCreateResponse>(appSettings, logger, "payment-create", Command.CREATE)

suspend fun ApplicationCall.statusPayment(appSettings: AppSettings, logger: SLogWrapper) =
    processPayment<PaymentStatusRequest, PaymentStatusResponse>(appSettings, logger, "payment-status", Command.STATUS)