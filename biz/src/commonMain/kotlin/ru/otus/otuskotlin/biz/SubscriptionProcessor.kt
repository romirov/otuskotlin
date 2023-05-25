package ru.otus.otuskotlin.biz

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.stubs.SubscriptionStub


class SubscriptionProcessor {
    suspend fun exec(ctx: Context) {
        ctx.subscriptionResponse = SubscriptionStub.get()
    }
}