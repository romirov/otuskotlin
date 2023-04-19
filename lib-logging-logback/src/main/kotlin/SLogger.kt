package ru.otus.otuskotlin.lib.logging.logback

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.logging.common.SLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun mpLoggerLogback(logger: Logger): SLogWrapper = SLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun mpLoggerLogback(clazz: KClass<*>): SLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun mpLoggerLogback(loggerId: String): SLogWrapper = mpLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)