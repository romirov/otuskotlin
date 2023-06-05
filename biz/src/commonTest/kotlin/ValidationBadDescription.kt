import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.WorkMode
import ru.otus.otuskotlin.stubs.SubscriptionStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = SubscriptionStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionCorrect(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = stub.id,
            title = "abc",
            description = "abc",
            subscriptionType = DealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
    assertEquals("abc", ctx.subscriptionValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = stub.id,
            title = "abc",
            description = " \n\tabc \n\t",
            subscriptionType = DealSide.DEMAND,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
    assertEquals("abc", ctx.subscriptionValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = stub.id,
            title = "abc",
            description = "",
            subscriptionType = DealSide.DEMAND,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = stub.id,
            title = "abc",
            description = "!@#$%^&*(),.{}",
            subscriptionType = DealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}