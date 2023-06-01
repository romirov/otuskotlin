import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.stubs.SubscriptionStub
import kotlin.test.assertEquals
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class AdSearchStubTest {

    private val processor = SubscriptionProcessor()
    val filter = Filter(searchString = "Postgre")

    @Test
    fun read() = runTest {

        val ctx = Context(
            command = Command.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.SUCCESS,
            subscriptionFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.subscriptionsResponse.size > 1)
        val first = ctx.subscriptionsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (SubscriptionStub.get()) {
            assertEquals(subscriptionType, first.subscriptionType)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = Context(
            command = Command.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_ID,
            subscriptionFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = Context(
            command = Command.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.DB_ERROR,
            subscriptionFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = Context(
            command = Command.SEARCH,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_TITLE,
            subscriptionFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}