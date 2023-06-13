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

class BizRepoReadTest {

    private val userId = UserId("321")
    private val command = Command.READ
    private val initAd = Subscription(
        id = SubscriptionRequestId("123"),
        title = "abc",
        description = "abc",
        subscriptionType = DealSide.DEMAND,
    )
    private val repo by lazy { SubscriptionRepositoryMock(
        invokeReadSubscription = {
            DbSubscriptionResponse(
                isSuccess = true,
                data = initAd,
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
    fun repoReadSuccessTest() = runTest {
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
        assertEquals(initAd.id, ctx.subscriptionResponse.id)
        assertEquals(initAd.title, ctx.subscriptionResponse.title)
        assertEquals(initAd.description, ctx.subscriptionResponse.description)
        assertEquals(initAd.subscriptionType, ctx.subscriptionResponse.subscriptionType)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
