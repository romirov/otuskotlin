import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import models.Command
import models.State
import org.otus.otuskotlin.api.v1.models.SubscriptionRequest
import org.otus.otuskotlin.api.v1.models.SubscriptionResponse
import ru.otus.otuskotlin.logging.common.SLogWrapper
import ru.otus.otuskotlin.mappers.fromSubscriptionTransport
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
                data = ctx.toLog("${logId}-got")
            )
            processor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
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
            ctx.errors.add(e.asMkplError())
            processor.exec(ctx)
            respond(ctx.toTransportSubscription())
        }
    }
}