import ru.otus.otuskotlin.logging.common.SLoggerProvider

data class CorSettings(
    val loggerProvider: SLoggerProvider = SLoggerProvider(),
) {
    companion object {
        val NONE = CorSettings()
    }
}