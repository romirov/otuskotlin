import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.otus.otuskotlin.api.v1.models.SubscriptionRequest
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = SubscriptionRequestId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            subscriptionType = DealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = SubscriptionRequestId(" \n\t 123-234-abc-ABC \n\t "),
            title = "abc",
            description = "abc",
            subscriptionType = DealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = SubscriptionRequestId(""),
            title = "abc",
            description = "abc",
            subscriptionType = DealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = SubscriptionRequestId("!@#\$%^&*(),.{}"),
            title = "abc",
            description = "abc",
            subscriptionType = DealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}