package plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.lib.logging.logback.sLoggerLogback
import ru.otus.otuskotlin.logging.common.SLoggerProvider

actual fun Application.getLoggerProviderConf(): SLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> SLoggerProvider { sLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and logback")
    }