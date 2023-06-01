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

@OptIn(ExperimentalCoroutinesApi::class)
class CreateStubTest {

    private val processor = SubscriptionProcessor()
    val id = SubscriptionRequestId("1")
    val title = "title 1"
    val description = "desc 1"
    val dealSide = DealSide.DEMAND

    @Test
    fun create() = runTest {

        val ctx = Context(
            command = Command.CREATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.SUCCESS,
            subscriptionRequest = Subscription(
                id = id,
                title = title,
                description = description,
                subscriptionType = dealSide,
            ),
        )
        processor.exec(ctx)
        assertEquals(SubscriptionStub.get().id, ctx.subscriptionResponse.id)
        assertEquals(title, ctx.subscriptionResponse.title)
        assertEquals(description, ctx.subscriptionResponse.description)
        assertEquals(dealSide, ctx.subscriptionResponse.subscriptionType)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = Context(
            command = Command.CREATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_TITLE,
            subscriptionRequest = Subscription(
                id = id,
                title = "",
                description = description,
                subscriptionType = dealSide,
            ),
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = Context(
            command = Command.CREATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_DESCRIPTION,
            subscriptionRequest = Subscription(
                id = id,
                title = title,
                description = "",
                subscriptionType = dealSide,
            ),
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = Context(
            command = Command.CREATE,
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
            command = Command.CREATE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_ID,
            subscriptionRequest = Subscription(
                id = id,
                title = title,
                description = description,
                subscriptionType = dealSide,
            ),
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}