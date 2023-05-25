package ru.otus.otuskotlin.common

import ru.otus.otuskotlin.lib.logging.common.SLoggerProvider

data class CorSettings(
    val loggerProvider: SLoggerProvider = SLoggerProvider(),
) {
    companion object {
        val NONE = CorSettings()
    }
}