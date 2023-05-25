package ru.otus.otuskotlin.subscription.app

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import org.otus.otuskotlin.api.v1.models.SubscriptionRequest
import org.otus.otuskotlin.api.v1.models.SubscriptionResponse
import ru.otus.otuskotlin.app.SAppSettings
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.helpers.asCommonError
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.lib.logging.common.SLogWrapper
import ru.otus.otuskotlin.mappers.fromSubscriptionTransport
import ru.otus.otuskotlin.mappers.log.toSubscriptionLog
import ru.otus.otuskotlin.mappers.toTransportSubscription

suspend inline fun <reified Q : SubscriptionRequest, @Suppress("unused") reified R : SubscriptionResponse> ApplicationCall.processSubscription(
    appSettings: SAppSettings,
    logger: SLogWrapper,
    logId: String,
    command: Command? = null,
) {
    val ctx = Context(
        timeStart = Clock.System.now(),
    )
    val processor = appSettings.subscriptionProcessor
    try {
        logger.doWithLogging(id = logId) {
            val request = receive<Q>()
            ctx.fromSubscriptionTransport(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toSubscriptionLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toSubscriptionLog("${logId}-handled")
            )
            respond(ctx.toTransportSubscription())
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
            respond(ctx.toTransportSubscription())
        }
    }
}