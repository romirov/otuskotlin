package ru.otus.otuskotlin.app

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.payment(appSettings: AppSettings) {
    val logger = appSettings.corSettings.loggerProvider.logger(Route::payment::class)
    route("payment") {
        post("create") {
            call.createPayment(appSettings, logger)
        }
        post("status") {
            call.statusPayment(appSettings, logger)
        }
    }
}