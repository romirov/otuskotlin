package plugins

import io.ktor.server.application.*
import ru.otus.otuskotlin.logging.common.SLoggerProvider

expect fun Application.getLoggerProviderConf(): SLoggerProvider