package ru.otus.otuskotlin.app

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.api.apiV1Mapper
import ru.otus.otuskotlin.app.plugins.swagger
import ru.otus.otuskotlin.lib.logging.logback.SLogWrapperLogback

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"

//@Suppress("unused") // Referenced in application.conf
fun Application.moduleJvm(appSettings: SAppSettings = initAppSettings()) {
    install(CallLogging) {
        level = Level.INFO
        val lgr = appSettings
            .corSettings
            .loggerProvider
            .logger(clazz) as? SLogWrapperLogback
        lgr?.logger?.also { logger = it }
    }
    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }
    install(DefaultHeaders)

    routing {
        route("v1") {
            subscription(appSettings)
            subscriptionOffers(appSettings)
            payment(appSettings)
        }
        swagger(appSettings)
        static("static") {
            resources("static")
        }
    }
}
