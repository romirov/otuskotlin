package com.subscription

import Context
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.otus.otuskotlin.api.v1.models.*
import models.DealSide
import ru.otus.otuskotlin.mappers.*


suspend fun ApplicationCall.createSubscription() {
    val request = receive<SubscriptionCreateRequest>()
    val context = Context()
    context.fromSubscriptionTransport(request)
    context.subscriptionResponse = SubscriptionStub.get()
    respond(context.toTransportSubscriptionCreate())
}

suspend fun ApplicationCall.readSubscription() {
    val request = receive<SubscriptionReadRequest>()
    val context = Context()
    context.fromSubscriptionTransport(request)
    context.subscriptionResponse = SubscriptionStub.get()
    respond(context.toTransportSubscriptionRead())
}

suspend fun ApplicationCall.updateSubscription() {
    val request = receive<SubscriptionUpdateRequest>()
    val context = Context()
    context.fromSubscriptionTransport(request)
    context.subscriptionResponse = SubscriptionStub.get()
    respond(context.toTransportSubscriptionUpdate())
}

suspend fun ApplicationCall.deleteSubscription() {
    val request = receive<SubscriptionDeleteRequest>()
    val context = Context()
    context.fromSubscriptionTransport(request)
    context.subscriptionResponse = SubscriptionStub.get()
    respond(context.toTransportSubscriptionDelete())
}

suspend fun ApplicationCall.searchSubscription() {
    val request = receive<SubscriptionSearchRequest>()
    val context = Context()
    context.fromSubscriptionTransport(request)
    context.subscriptionsResponse.addAll(SubscriptionStub.prepareSearchList("Подписка", DealSide.DEMAND))
    respond(context.toTransportSubscriptionSearch())
}

suspend fun ApplicationCall.statusSubscription() {
    val request = receive<SubscriptionSearchRequest>()
    val context = Context()
    context.fromSubscriptionTransport(request)
    context.subscriptionsResponse.addAll(SubscriptionStub.prepareSearchList("Подписка", DealSide.DEMAND))
    respond(context.toTransportSubscriptionStatus())
}

suspend fun ApplicationCall.offersSubscription() {
    val request = receive<SubscriptionOffersRequest>()
    val context = Context()
    context.fromSubscriptionTransport(request)
    context.subscriptionsResponse.addAll(SubscriptionStub.prepareOffersList("Подписка", DealSide.SUPPLY))
    respond(context.toTransportSubscriptionOffers())
}