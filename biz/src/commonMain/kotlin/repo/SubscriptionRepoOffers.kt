package ru.otus.otuskotlin.biz.repo

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.CommonError
import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.repo.DbSubscriptionFilterRequest
import ru.otus.otuskotlin.common.repo.DbSubscriptionsResponse
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.repoOffersSubscription(title: String) = worker {
    this.title = title
    description = "Поиск предложений для объявления по названию"
    on { state == State.RUNNING }
    handle {
        val subscriptionRequest = subscriptionRepoPrepare
        val filter = DbSubscriptionFilterRequest(
            titleFilter = subscriptionRequest.title,
            dealSide = when (subscriptionRequest.subscriptionType) {
                DealSide.DEMAND -> DealSide.SUPPLY
                DealSide.SUPPLY -> DealSide.DEMAND
                DealSide.NONE -> DealSide.NONE
            }
        )
        val dbResponse = if (filter.dealSide == DealSide.NONE) {
            DbSubscriptionsResponse(
                data = null,
                isSuccess = false,
                errors = listOf(
                    CommonError(
                        field = "subscriptionType",
                        message = "Type of ad must not be empty"
                    )
                )
            )
        } else {
            subscriptionRepo.searchSubscription(filter)
        }

        val resultSubscriptions = dbResponse.data
        when {
            !resultSubscriptions.isNullOrEmpty() -> subscriptionsRepoDone = resultSubscriptions.toMutableList()
            dbResponse.isSuccess -> return@handle
            else -> {
                state = State.FAILING
                errors.addAll(dbResponse.errors)
            }
        }
    }
}