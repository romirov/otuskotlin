package ru.otus.otuskotlin.mappers

import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.CommonDealSide
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
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
    responseType = "create",
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionRead() = SubscriptionReadResponse(
    responseType = "read",
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionUpdate() = SubscriptionUpdateResponse(
    responseType = "update",
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionDelete() = SubscriptionDeleteResponse(
    responseType = "delete",
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionSearch() = SubscriptionSearchResponse(
    responseType = "search",
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscriptions = subscriptionsResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionStatus() = SubscriptionStatusResponse(
    responseType = "status",
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscription = subscriptionResponse.toTransportSubscription()
)

fun Context.toTransportSubscriptionOffers() = SubscriptionOffersResponse(
    responseType = "offers",
    requestId = this.subscriptionRequestId.asString().takeIf { it.isNotBlank() },
    result = if (state == State.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    subscriptions = subscriptionsResponse.toTransportSubscription()
)

fun List<Subscription>.toTransportSubscription(): List<SubscriptionResponseObject>? = this
    .map { it.toTransportSubscription() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Subscription.toTransportSubscription(): SubscriptionResponseObject = SubscriptionResponseObject(
    id = id.takeIf { it != SubscriptionRequestId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    subscriptionType = subscriptionType.toTransportSubscription()
)

private fun CommonDealSide.toTransportSubscription(): DealSide? = when (this) {
    CommonDealSide.DEMAND -> DealSide.DEMAND
    CommonDealSide.SUPPLY -> DealSide.SUPPLY
    CommonDealSide.NONE -> null
}

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
