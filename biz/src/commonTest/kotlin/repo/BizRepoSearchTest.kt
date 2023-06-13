package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.otus.otuskotlin.biz.SubscriptionProcessor
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.*
import ru.otus.otuskotlin.common.repo.DbSubscriptionsResponse
import ru.otus.otuskotlin.repo.tests.SubscriptionRepositoryMock
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = UserId("321")
    private val command = Command.SEARCH
    private val initAd = Subscription(
        id = SubscriptionRequestId("123"),
        title = "abc",
        description = "abc",
        subscriptionType = DealSide.DEMAND,
    )
    private val repo by lazy { SubscriptionRepositoryMock(
        invokeSearchSubscription = {
            DbSubscriptionsResponse(
                isSuccess = true,
                data = listOf(initAd),
            )
        }
    ) }
    private val settings by lazy {
        CorSettings(
            repoSubscriptionTest = repo
        )
    }
    private val processor by lazy { SubscriptionProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = Context(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            subscriptionFilterRequest = SubscriptionFilter(
                searchString = "ab",
                dealSide = DealSide.DEMAND
            ),
        )
        processor.exec(ctx)
        assertEquals(State.FINISHING, ctx.state)
        assertEquals(1, ctx.subscriptionsResponse.size)
    }
}
