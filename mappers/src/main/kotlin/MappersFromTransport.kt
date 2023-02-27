package ru.otus.otuskotlin.mappers

import Context
import models.*
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.mappers.exceptions.UnknownRequestClass
import stubs.Stubs

fun Context.fromTransport(request: IRequest) = when (request) {
    is SubscriptionCreateRequest -> fromTransport(request)
    is SubscriptionReadRequest -> fromTransport(request)
    is SubscriptionUpdateRequest -> fromTransport(request)
    is SubscriptionDeleteRequest -> fromTransport(request)
    is SubscriptionSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { SubscriptionId(it) } ?: SubscriptionId.NONE
private fun String?.toAdWithId() = Subscription(id = this.toAdId())
private fun IRequest?.requestId() = this?.requestId?.let { RequestId(it) } ?: RequestId.NONE

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

fun Context.fromTransport(request: SubscriptionCreateRequest) {
    command = Command.CREATE
    requestId = request.requestId()
    subscriptionRequest = request.subscription?.toInternal() ?: Subscription()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromTransport(request: SubscriptionReadRequest) {
    command = Command.READ
    requestId = request.requestId()
    subscriptionRequest = request.subscription?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromTransport(request: SubscriptionUpdateRequest) {
    command = Command.UPDATE
    requestId = request.requestId()
    subscriptionRequest = request.subscription?.toInternal() ?: Subscription()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromTransport(request: SubscriptionDeleteRequest) {
    command = Command.DELETE
    requestId = request.requestId()
    subscriptionRequest = request.subscription?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun Context.fromTransport(request: SubscriptionSearchRequest) {
    command = Command.SEARCH
    requestId = request.requestId()
    subscriptionFilterRequest = request.subscriptionFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun SubscriptionSearchFilter?.toInternal(): Filter = Filter(
    searchString = this?.searchString ?: ""
)

private fun SubscriptionCreateObject.toInternal(): Subscription = Subscription(
    title = this.title ?: "",
    description = this.description ?: ""
)

private fun SubscriptionUpdateObject.toInternal(): Subscription = Subscription(
    id = this.id.toAdId(),
    title = this.title ?: "",
    description = this.description ?: ""
)
