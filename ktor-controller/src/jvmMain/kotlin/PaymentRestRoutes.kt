package ru.otus.otuskotlin.app

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.app.SAppSettings
import ru.otus.otuskotlin.app.createPayment
import ru.otus.otuskotlin.app.statusPayment

fun Route.payment(appSettings: SAppSettings) {
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