package ru.otus.otuskotlin.app

import io.ktor.server.application.*
import models.Command
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.logging.common.SLogWrapper

suspend fun ApplicationCall.createSubscription(appSettings: SAppSettings, logger: SLogWrapper) =
    processSubscription<SubscriptionCreateRequest, SubscriptionCreateResponse>(appSettings, logger, "subscription-create", Command.CREATE)

suspend fun ApplicationCall.readSubscription(appSettings: SAppSettings, logger: SLogWrapper) =
    processSubscription<SubscriptionReadRequest, SubscriptionReadResponse>(appSettings, logger, "subscription-read", Command.READ)

suspend fun ApplicationCall.updateSubscription(appSettings: SAppSettings, logger: SLogWrapper) =
    processSubscription<SubscriptionUpdateRequest, SubscriptionUpdateResponse>(appSettings, logger, "subscription-update", Command.UPDATE)

suspend fun ApplicationCall.deleteSubscription(appSettings: SAppSettings, logger: SLogWrapper) =
    processSubscription<SubscriptionDeleteRequest, SubscriptionDeleteResponse>(appSettings, logger, "subscription-delete", Command.DELETE)

suspend fun ApplicationCall.searchSubscription(appSettings: SAppSettings,logger: SLogWrapper) =
    processSubscription<SubscriptionSearchRequest, SubscriptionSearchResponse>(appSettings, logger, "subscription-search", Command.SEARCH)

suspend fun ApplicationCall.statusSubscription(appSettings: SAppSettings,logger: SLogWrapper) =
    processSubscription<SubscriptionStatusRequest, SubscriptionStatusResponse>(appSettings, logger, "subscription-status", Command.STATUS)

suspend fun ApplicationCall.offersSubscription(appSettings: SAppSettings,logger: SLogWrapper) =
    processSubscription<SubscriptionOffersRequest, SubscriptionOffersResponse>(appSettings, logger, "subscription-offers", Command.OFFERS)