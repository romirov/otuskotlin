package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.common.repo.DbSubscriptionResponse
import ru.otus.otuskotlin.repo.tests.SubscriptionRepositoryMock
import kotlin.test.assertEquals

private val initAd = Subscription(
    id = SubscriptionRequestId("123"),
    title = "abc",
    description = "abc",
    subscriptionType = DealSide.DEMAND,
)
private val repo = SubscriptionRepositoryMock(
    invokeReadSubscription = {
        if (it.id == initAd.id) {
            DbSubscriptionResponse(
                isSuccess = true,
                data = initAd,
            )
        } else DbSubscriptionResponse(
            isSuccess = false,
            data = null,
            errors = listOf(CommonError(message = "Not found", field = "id"))
        )
    }
)
private val settings by lazy {
    CorSettings(
        repoSubscriptionTest = repo
    )
}
private val processor by lazy { SubscriptionProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: Command) = runTest {
    val ctx = Context(
        command = command,
        state = State.NONE,
        workMode = WorkMode.TEST,
        subscriptionRequest = Subscription(
            id = SubscriptionRequestId("12345"),
            title = "xyz",
            description = "xyz",
            subscriptionType = DealSide.DEMAND,
        ),
    )
    processor.exec(ctx)
    assertEquals(State.FAILING, ctx.state)
    assertEquals(Subscription(), ctx.subscriptionResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}