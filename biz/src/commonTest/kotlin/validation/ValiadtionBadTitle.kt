package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.stubs.SubscriptionStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = SubscriptionStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleCorrect(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = stub.id,
            title = "abc",
            description = "abc",
            subscriptionType = CommonDealSide.DEMAND,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
    assertEquals("abc", ctx.subscriptionValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleTrim(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = stub.id,
            title = " \n\t abc \t\n ",
            description = "abc",
            subscriptionType = CommonDealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(State.FAILING, ctx.state)
    assertEquals("abc", ctx.subscriptionValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = stub.id,
            title = "",
            description = "abc",
            subscriptionType = CommonDealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: Command, processor: SubscriptionProcessor) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = SubscriptionRequestId("123"),
            title = "!@#$%^&*(),.{}",
            description = "abc",
            subscriptionType = CommonDealSide.DEMAND
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(State.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
