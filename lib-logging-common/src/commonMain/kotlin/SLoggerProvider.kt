package ru.otus.otuskotlin.lib.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class SLoggerProvider(
    private val provider: (String) -> SLogWrapper = { SLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    fun logger(function: KFunction<*>) = provider(function.name)
}