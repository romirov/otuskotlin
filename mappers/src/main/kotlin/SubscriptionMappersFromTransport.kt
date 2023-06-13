package ru.otus.otuskotlin.mappers

import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.SubscriptionFilter
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.models.WorkMode
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.mappers.exceptions.UnknownRequestClass

fun Context.fromSubscriptionTransport(request: SubscriptionRequest) = when (request) {
    is SubscriptionCreateRequest -> fromSubscriptionTransport(request)
    is SubscriptionReadRequest -> fromSubscriptionTransport(request)
    is SubscriptionUpdateRequest -> fromSubscriptionTransport(request)
    is SubscriptionDeleteRequest -> fromSubscriptionTransport(request)
    is SubscriptionSearchRequest -> fromSubscriptionTransport(request)
    is SubscriptionStatusRequest -> fromSubscriptionTransport(request)
    is SubscriptionOffersRequest -> fromSubscriptionTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toSubscriptionId() = this?.let { SubscriptionRequestId(it) } ?: SubscriptionRequestId.NONE
private fun String?.toSubscriptionWithId() = Subscription(id = this.toSubscriptionId())
private fun SubscriptionRequest?.requestId() = this?.requestId?.let { SubscriptionRequestId(it) } ?: SubscriptionRequestId.NONE

private fun SubscriptionDebug?.transportToWorkMode(): WorkMode = when (this?.mode) {
    SubscriptionRequestDebugMode.PROD -> WorkMode.PROD
    SubscriptionRequestDebugMode.TEST -> WorkMode.TEST
    SubscriptionRequestDebugMode.STUB -> WorkMode.STUB
    null -> WorkMode.PROD
}

private fun SubscriptionDebug?.transportToStubCase(): Stubs = when (this?.stub) {
    SubscriptionRequestDebugStubs.SUCCESS -> Stubs.SUCCESS
    SubscriptionRequestDebugStubs.NOT_FOUND -> Stubs.NOT_FOUND
    SubscriptionRequestDebugStubs.BAD_ID -> Stubs.BAD_ID
    SubscriptionRequestDebugStubs.BAD_TITLE -> Stubs.BAD_TITLE
    SubscriptionRequestDebugStubs.BAD_DESCRIPTION -> Stubs.BAD_DESCRIPTION
    SubscriptionRequestDebugStubs.CANNOT_DELETE -> Stubs.CANNOT_DELETE
    SubscriptionRequestDebugStubs.BAD_SEARCH_STRING -> Stubs.BAD_SEARCH_STRING
    null -> Stubs.NONE
}

fun Context.fromSubscriptionTransport(request: SubscriptionCreateRequest) {
    command = Command.CREATE
    subscriptionRequestId = request.requestId()
    subscriptionRequest = request.subscription?.toInternal() ?: Subscription()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromSubscriptionTransport(request: SubscriptionReadRequest) {
    command = Command.READ
    subscriptionRequestId = request.requestId()
    subscriptionRequest = request.subscription?.id.toSubscriptionWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromSubscriptionTransport(request: SubscriptionUpdateRequest) {
    command = Command.UPDATE
    subscriptionRequestId = request.requestId()
    subscriptionRequest = request.subscription?.toInternal() ?: Subscription()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromSubscriptionTransport(request: SubscriptionDeleteRequest) {
    command = Command.DELETE
    subscriptionRequestId = request.requestId()
    subscriptionRequest = request.subscription?.id.toSubscriptionWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromSubscriptionTransport(request: SubscriptionSearchRequest) {
    command = Command.SEARCH
    subscriptionRequestId = request.requestId()
    subscriptionFilterRequest = request.subscriptionFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromSubscriptionTransport(request: SubscriptionStatusRequest) {
    command = Command.STATUS
    subscriptionRequestId = request.requestId()
    subscriptionRequest = request.subscription?.toInternal() ?: Subscription()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromSubscriptionTransport(request: SubscriptionOffersRequest) {
    command = Command.OFFERS
    subscriptionRequestId = request.requestId()
    subscriptionRequest = request.subscription?.id.toSubscriptionWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun SubscriptionSearchFilter?.toInternal(): SubscriptionFilter = SubscriptionFilter(
    searchString = this?.searchString ?: ""
)

private fun SubscriptionCreateObject.toInternal(): Subscription = Subscription(
    title = this.title ?: "",
    description = this.description ?: ""
)

private fun SubscriptionUpdateObject.toInternal(): Subscription = Subscription(
    id = this.id.toSubscriptionId(),
    title = this.title ?: "",
    description = this.description ?: ""
)

private fun SubscriptionStatusObject.toInternal(): Subscription = Subscription(
    id = this.id.toSubscriptionId()
)