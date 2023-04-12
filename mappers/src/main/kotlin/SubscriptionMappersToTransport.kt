package ru.otus.otuskotlin.mappers

import Context
import models.*
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.mappers.exceptions.UnknownSubscriptionCommand

fun Context.toTransportSubscription(): SubscriptionResponse = when (val cmd = command) {
    Command.CREATE -> toTransportSubscriptionCreate()
    Command.READ -> toTransportSubscriptionRead()
    Command.UPDATE -> toTransportSubscriptionUpdate()
    Command.DELETE -> toTransportSubscriptionDelete()
    Command.SEARCH -> toTransportSubscriptionSearch()
    Command.STATUS -> toTransportSubscriptionStatus()
    Command.OFFERS -> toTransportSubscriptionOffers()
    else -> throw UnknownSubscriptionCommand(cmd)
}

fun Context.toTransportSubscriptionCreate() = SubscriptionCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionRead() = SubscriptionReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionUpdate() = SubscriptionUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionDelete() = SubscriptionDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionSearch() = SubscriptionSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscriptions = subscriptionsResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionStatus() = SubscriptionStatusResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionOffers() = SubscriptionOffersResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscriptions = subscriptionsResponse.toTransportSubscription()
)

fun List<Subscription>.toTransportSubscription(): List<SubscriptionResponseObject>? = this
    .map { it.toTransportSubscription() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Subscription.toTransportSubscription(): SubscriptionResponseObject = SubscriptionResponseObject(
    id = id.takeIf { it != SubscriptionId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
)

private fun List<CommonError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportSubscription() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CommonError.toTransportSubscription() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
