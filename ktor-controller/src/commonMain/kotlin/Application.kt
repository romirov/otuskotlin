package ru.otus.otuskotlin.app

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import ru.otus.otuskotlin.app.plugins.initAppSettings
import ru.otus.otuskotlin.app.plugins.initPlugins

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module(appSettings: AppSettings = initAppSettings()) {
    initPlugins(appSettings)
}
