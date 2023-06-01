import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.models.WorkMode
import ru.otus.otuskotlin.common.stubs.Stubs
import ru.otus.otuskotlin.stubs.SubscriptionStub
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AdDeleteStubTest {

    private val processor = SubscriptionProcessor()
    val id = SubscriptionRequestId("1")

    @Test
    fun delete() = runTest {

        val ctx = Context(
            command = Command.DELETE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.SUCCESS,
            subscriptionRequest = Subscription(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = SubscriptionStub.get()
        assertEquals(stub.id, ctx.subscriptionResponse.id)
        assertEquals(stub.title, ctx.subscriptionResponse.title)
        assertEquals(stub.description, ctx.subscriptionResponse.description)
        assertEquals(stub.subscriptionType, ctx.subscriptionResponse.subscriptionType)
    }

    @Test
    fun badId() = runTest {
        val ctx = Context(
            command = Command.DELETE,
            state = State.NONE,
            workMode = WorkMode.STUB,
            stubCase = Stubs.BAD_ID,
            subscriptionRequest = Subscription(),
        )
        processor.exec(ctx)
        assertEquals(Subscription(), ctx.subscriptionResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = Context(
            command = Command.DELETE,
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
            command = Command.DELETE,
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
