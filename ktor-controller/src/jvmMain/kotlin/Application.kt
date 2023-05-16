package ru.otus.otuskotlin.app

import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.api.apiV1Mapper
import ru.otus.otuskotlin.app.plugins.initAppSettings
import ru.otus.otuskotlin.app.plugins.swagger
import ru.otus.otuskotlin.lib.logging.logback.SLogWrapperLogback

private val clazz = Application::module::class.qualifiedName ?: "Application"

@Suppress("unused") // Referenced in application.conf_
fun Application.module(appSettings: SAppSettings = initAppSettings()) {
    // Generally not needed as it is replaced by a `routing`
    install(Routing)

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }

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

    @Suppress("OPT_IN_USAGE")
    install(Locations)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v1") {
            subscription(appSettings)
            subscriptionOffers(appSettings)
        }

        route("v1") {
            payment(appSettings)
        }

        static("static") {
            resources("static")
        }

        swagger(appSettings)
        static("static") {
            resources("static")
        }
    }
}
