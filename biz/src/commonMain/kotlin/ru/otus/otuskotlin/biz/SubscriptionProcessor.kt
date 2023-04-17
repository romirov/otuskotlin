package ru.otus.otuskotlin.biz

import Context
import SubscriptionStub

class SubscriptionProcessor {
    suspend fun exec(ctx: Context) {
        ctx.subscriptionResponse = SubscriptionStub.get()
    }
}