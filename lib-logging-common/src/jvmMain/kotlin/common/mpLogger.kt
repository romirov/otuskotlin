package common

import ch.qos.logback.classic.Logger
import jvm.mpLoggerJvm
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

@Suppress("unused")
actual fun mpLogger(loggerId: String) = mpLoggerJvm(
    logger = LoggerFactory.getLogger(loggerId) as Logger
)

actual fun mpLogger(cls: KClass<*>) = mpLoggerJvm(
    logger = LoggerFactory.getLogger(cls.java) as Logger
)