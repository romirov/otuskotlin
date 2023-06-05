package ru.otus.otuskotlin.lib.logging.common.impl

import ru.otus.otuskotlin.lib.logging.common.LogLevel
import ru.otus.otuskotlin.lib.logging.common.SLogWrapper
import java.util.logging.Logger

class MpLoggerWrapperCommon(
    val logger: Logger,
    override val loggerId: String
) : SLogWrapper {

    override fun log(
        msg: String,
        level: LogLevel,
        marker: String,
        e: Throwable?,
        data: Any?,
        objs: Map<String, Any>?,
    ) {
        logger.log(
            tag = marker,
            throwable = e,
            message = formatMessage(msg, data, objs),
        )
    }

    private inline fun formatMessage(
        msg: String = "",
        data: Any? = null,
        objs: Map<String, Any>?,
    ): String {
        var message = msg
        data?.let {
            message += "\n" + data.toString()
        }
        objs?.forEach {
            message += "\n" + it.toString()
        }
        return message
    }

}