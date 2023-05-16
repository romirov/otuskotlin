package ru.otus.otuskotlin.app

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.app.*

fun Route.subscription(appSettings: SAppSettings) {
    val logger = appSettings.corSettings.loggerProvider.logger(Route::subscription::class)
    route("subscription") {
        post("create") {
            call.createSubscription(appSettings, logger)
        }
        post("read") {
            call.readSubscription(appSettings, logger)
        }
        post("update") {
            call.updateSubscription(appSettings, logger)
        }
        post("delete") {
            call.deleteSubscription(appSettings, logger)
        }
        post("search") {
            call.searchSubscription(appSettings, logger)
        }
        post("status") {
            call.statusSubscription(appSettings, logger)
        }
    }
}

fun Route.subscriptionOffers(appSettings: SAppSettings) {
    val logger = appSettings.corSettings.loggerProvider.logger(Route::subscriptionOffers::class)
    route("subscription") {
        post("offers") {
            call.offersSubscription(appSettings, logger)
        }
    }
}