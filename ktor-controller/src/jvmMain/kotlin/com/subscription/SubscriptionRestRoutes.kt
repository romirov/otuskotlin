package com.subscription

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.subscription() {
    route("subscription") {
        post("create") {
            call.createSubscription()
        }
        post("read") {
            call.readSubscription()
        }
        post("update") {
            call.updateSubscription()
        }
        post("delete") {
            call.deleteSubscription()
        }
        post("search") {
            call.searchSubscription()
        }
        post("status") {
            call.statusSubscription()
        }
    }
}