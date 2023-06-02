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
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class OffersStubTest {

    private val processor = SubscriptionProcessor()
    val id = SubscriptionRequestId("2")

    @Test
    fun offers() = runTest {

        val ctx = Context(
            command = Command.OFFERS,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.SUCCESS,
            subscriptionRequest = Subscription(
                id = id,
            ),
        )
        processor.exec(ctx)

        assertEquals(id, ctx.subscriptionResponse.id)

        with(SubscriptionStub.get()) {
            assertEquals(title, ctx.subscriptionResponse.title)
            assertEquals(description, ctx.subscriptionResponse.description)
            assertEquals(subscriptionType, ctx.subscriptionResponse.subscriptionType)
        }

        assertTrue(ctx.subscriptionsResponse.size > 1)
        val first = ctx.subscriptionsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(ctx.subscriptionResponse.title))
        assertTrue(first.description.contains(ctx.subscriptionResponse.title))
        assertEquals(DealSide.SUPPLY, first.subscriptionType)
    }

    @Test
    fun badId() = runTest {
        val ctx = Context(
            command = Command.OFFERS,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_ID,
            subscriptionRequest = Subscription(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = Context(
            command = Command.OFFERS,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.DB_ERROR,
            subscriptionRequest = Subscription(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = Context(
            command = Command.OFFERS,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_TITLE,
            subscriptionRequest = Subscription(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
