import org.junit.Test
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.SubscriptionRequestId

class BizValidationCreateTest {

    private val command = Command.CREATE
    private val processor by lazy { SubscriptionProcessor() }

    @Test
    fun correctTitle() = validationTitleCorrect(command, processor)
    @Test
    fun trimTitle() = validationTitleTrim(command, processor)
    @Test
    fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test
    fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test
    fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test
    fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test
    fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test
    fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)
}