package validation

import SubscriptionRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.common.models.Command

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationReadTest {

    private val command = Command.READ
    private val settings by lazy {
        CorSettings(
            repoSubscriptionTest = SubscriptionRepoStub()
        )
    }
    private val processor by lazy { SubscriptionProcessor(settings) }

    @Test
    fun correctId() = validationIdCorrect(command, processor)

    @Test
    fun trimId() = validationIdTrim(command, processor)

    @Test
    fun emptyId() = validationIdEmpty(command, processor)

    @Test
    fun badFormatId() = validationIdFormat(command, processor)

}