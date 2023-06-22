package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.common.repo.DbSubscriptionResponse
import ru.otus.otuskotlin.repo.tests.SubscriptionRepositoryMock
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = UserId("321")
    private val command = Command.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = SubscriptionRepositoryMock(
        invokeCreateSubscription = {
            DbSubscriptionResponse(
                isSuccess = true,
                data = Subscription(
                    id = SubscriptionRequestId(uuid),
                    title = it.subscription.title,
                    description = it.subscription.description,
                    subscriptionType = it.subscription.subscriptionType,
                )
            )
        }
    )
    private val settings = CorSettings(
        repoSubscriptionTest = repo
    )
    private val processor = SubscriptionProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = Context(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            subscriptionRequest = Subscription(
                title = "abc",
                description = "abc",
                subscriptionType = CommonDealSide.DEMAND,
            ),
        )
        processor.exec(ctx)
        assertEquals(State.FINISHING, ctx.state)
        assertNotEquals(SubscriptionRequestId.NONE, ctx.subscriptionResponse.id)
        assertEquals("abc", ctx.subscriptionResponse.title)
        assertEquals("abc", ctx.subscriptionResponse.description)
        assertEquals(CommonDealSide.DEMAND, ctx.subscriptionResponse.subscriptionType)
    }
}