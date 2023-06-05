package ru.otus.otuskotlin.lib.logging.common.common

import ru.otus.otuskotlin.lib.logging.common.SLogWrapper
import kotlin.reflect.KClass

expect fun mpLogger(loggerId: String): SLogWrapper

expect fun mpLogger(cls: KClass<*>): SLogWrapper
