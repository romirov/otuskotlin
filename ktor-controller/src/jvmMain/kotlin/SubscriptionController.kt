import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import models.Command
import org.otus.otuskotlin.api.v1.models.*
import models.DealSide
import models.Subscription
import ru.otus.otuskotlin.logging.common.SLogWrapper
import ru.otus.otuskotlin.mappers.*

suspend fun ApplicationCall.createSubscription(appSettings: SAppSettings, logger: SLogWrapper) =
    processV1<SubscriptionCreateRequest, SubscriptionCreateResponse>(appSettings, logger, "subscription-create", Command.CREATE)

suspend fun ApplicationCall.readSubscription(appSettings: SAppSettings, logger: SLogWrapper) =
    processV1<SubscriptionReadRequest, SubscriptionReadResponse>(appSettings, logger, "subscription-read", Command.READ)

suspend fun ApplicationCall.updateSubscription(appSettings: SAppSettings, logger: SLogWrapper) =
    processV1<SubscriptionUpdateRequest, SubscriptionUpdateResponse>(appSettings, logger, "subscription-update", Command.UPDATE)

suspend fun ApplicationCall.deleteAd(appSettings: SAppSettings, logger: SLogWrapper) =
    processV1<SubscriptionDeleteRequest, SubscriptionDeleteResponse>(appSettings, logger, "subscription-delete", Command.DELETE)

suspend fun ApplicationCall.searchSubscription(appSettings: SAppSettings,logger: SLogWrapper) =
    processV1<SubscriptionSearchRequest, SubscriptionSearchResponse>(appSettings, logger, "subscription-search", Command.SEARCH)

suspend fun ApplicationCall.statusSubscription(appSettings: SAppSettings,logger: SLogWrapper) =
    processV1<SubscriptionStatusRequest, SubscriptionStatusResponse>(appSettings, logger, "subscription-status", Command.STATUS)

suspend fun ApplicationCall.offersSubscription(appSettings: SAppSettings,logger: SLogWrapper) =
    processV1<SubscriptionOffersRequest, SubscriptionOffersResponse>(appSettings, logger, "subscription-offers", Command.OFFERS)