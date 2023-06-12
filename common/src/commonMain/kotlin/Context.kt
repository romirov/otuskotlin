package ru.otus.otuskotlin.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.common.repo.IPaymentRepository
import ru.otus.otuskotlin.common.repo.ISubscriptionRepository
import ru.otus.otuskotlin.common.stubs.Stubs

data class Context(
    var command: Command = Command.NONE,
    var state: State = State.NONE,
    val errors: MutableList<CommonError> = mutableListOf(),
    var settings: CorSettings = CorSettings.NONE,

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: Stubs = Stubs.NONE,

    var subscriptionRepo: ISubscriptionRepository = ISubscriptionRepository.NONE,
    var subscriptionRepoRead: Subscription = Subscription(),
    var subscriptionRepoPrepare: Subscription = Subscription(),
    var subscriptionRepoDone: Subscription = Subscription(),
    var subscriptionsRepoDone: MutableList<Subscription> = mutableListOf(),

    var paymentRepo: IPaymentRepository = IPaymentRepository.NONE,
    var paymentRepoRead: Payment = Payment(),
    var paymentRepoPrepare: Payment = Payment(),
    var paymentRepoDone: Payment = Payment(),
    var paymentsRepoDone: MutableList<Payment> = mutableListOf(),

    var subscriptionValidating: Subscription = Subscription(),
    var subscriptionFilterValidating: Filter = Filter(),

    var subscriptionValidated: Subscription = Subscription(),
    var subscriptionFilterValidated: Filter = Filter(),

    var paymentValidating: Payment = Payment(),
    var paymentFilterValidating: Filter = Filter(),

    var paymentValidated: Payment = Payment(),
    var paymentFilterValidated: Filter = Filter(),

    var timeStart: Instant = Instant.NONE,
    var subscriptionRequestId: SubscriptionRequestId = SubscriptionRequestId.NONE,
    var subscriptionRequest: Subscription = Subscription(),
    var subscriptionFilterRequest: Filter = Filter(),
    var subscriptionResponse: Subscription = Subscription(),
    var subscriptionsResponse: MutableList<Subscription> = mutableListOf(),

    var paymentRequestId: PaymentRequestId = PaymentRequestId.NONE,
    var paymentRequest: Payment = Payment(),
    var paymentResponse: Payment = Payment(),
    var paymentsResponse: MutableList<Payment> = mutableListOf(),
)