package impl;

fun mpLoggerCommon(loggerId: String): SLogWrapper {
        val logger = Logger(
        config = StaticConfig(
        minSeverity = Severity.Info,
        ),
        tag = "DEV"
        )
        return MpLoggerWrapperCommon(
        logger = logger,
        loggerId = loggerId,
        )
        }

        fun mpLoggerCommon(cls: KClass<*>): SLogWrapper {
        val logger = Logger(
        config = StaticConfig(
        minSeverity = Severity.Info,
        ),
        tag = "DEV"
        )
        return MpLoggerWrapperCommon(
        logger = logger,
        loggerId = cls.qualifiedName?: "",
        )
}