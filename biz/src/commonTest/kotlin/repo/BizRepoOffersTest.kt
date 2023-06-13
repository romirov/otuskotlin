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
import ru.otus.otuskotlin.common.repo.DbSubscriptionsResponse
import ru.otus.otuskotlin.repo.tests.SubscriptionRepositoryMock
import kotlin.test.assertEquals

class BizRepoOffersTest {

    private val userId = UserId("321")
    private val command = Command.OFFERS
    private val initAd = Subscription(
        id = SubscriptionRequestId("123"),
        title = "abc",
        description = "abc",
        subscriptionType = DealSide.DEMAND,
    )
    private val offerAd = Subscription(
        id = SubscriptionRequestId("321"),
        title = "abcd",
        description = "xyz",
        subscriptionType = DealSide.SUPPLY,
    )
    private val repo by lazy { SubscriptionRepositoryMock(
        invokeReadSubscription = {
            DbSubscriptionResponse(
                isSuccess = true,
                data = initAd
            )
        },
        invokeSearchSubscription = {
            DbSubscriptionsResponse(
                isSuccess = true,
                data = listOf(offerAd)
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
    fun repoOffersSuccessTest() = runTest {
        val ctx = Context(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            subscriptionRequest = Subscription(
                id = SubscriptionRequestId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(State.FINISHING, ctx.state)
        assertEquals(1, ctx.subscriptionsResponse.size)
        assertEquals(DealSide.SUPPLY, ctx.subscriptionsResponse.first().subscriptionType)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersNotFoundTest() = repoNotFoundTest(command)
}