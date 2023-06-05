package jvm

import ch.qos.logback.classic.Logger
import ru.otus.otuskotlin.lib.logging.common.SLogWrapper

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun mpLoggerJvm(logger: Logger): SLogWrapper = SLogWrapperJvm(
    logger = logger,
    loggerId = logger.name,
)