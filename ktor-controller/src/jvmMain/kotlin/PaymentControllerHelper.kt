package ru.otus.otuskotlin.app

import Context
import helpers.asCommonError
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import models.Command
import models.State
import org.otus.otuskotlin.api.v1.models.PaymentRequest
import org.otus.otuskotlin.api.v1.models.PaymentResponse
import ru.otus.otuskotlin.logging.common.SLogWrapper
import ru.otus.otuskotlin.mappers.fromPaymentTransport
import ru.otus.otuskotlin.mappers.toTransportPayment
import toPaymentLog

suspend inline fun <reified Q : PaymentRequest, @Suppress("unused") reified R : PaymentResponse> ApplicationCall.processPayment(
    appSettings: SAppSettings,
    logger: SLogWrapper,
    logId: String,
    command: Command? = null,
) {
    val ctx = Context(
        timeStart = Clock.System.now(),
    )
    val processor = appSettings.paymentProcessor
    try {
        logger.doWithLogging(id = logId) {
            val request = receive<Q>()
            ctx.fromPaymentTransport(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toPaymentLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toPaymentLog("${logId}-handled")
            )
            respond(ctx.toTransportPayment())
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
            )
            ctx.state = State.FAILING
            ctx.errors.add(e.asCommonError())
            processor.exec(ctx)
            respond(ctx.toTransportPayment())
        }
    }
}