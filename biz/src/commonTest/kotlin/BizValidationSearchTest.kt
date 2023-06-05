import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.Filter
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.WorkMode
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = Command.SEARCH
    private val processor by lazy { SubscriptionProcessor() }

    @Test
    fun correctEmpty() = runTest {
        val ctx = Context(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            subscriptionFilterRequest = Filter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(State.FAILING, ctx.state)
    }
}