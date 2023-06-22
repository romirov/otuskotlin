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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = UserId("321")
    private val command = Command.DELETE
    private val initAd = Subscription(
        id = SubscriptionRequestId("123"),
        title = "abc",
        description = "abc",
        subscriptionType = CommonDealSide.DEMAND,
    )
    private val repo by lazy {
        SubscriptionRepositoryMock(
            invokeReadSubscription = {
                DbSubscriptionResponse(
                    isSuccess = true,
                    data = initAd,
                )
            },
            invokeDeleteSubscription = {
                if (it.id == initAd.id)
                    DbSubscriptionResponse(
                        isSuccess = true,
                        data = initAd
                    )
                else DbSubscriptionResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        CorSettings(
            repoSubscriptionTest = repo
        )
    }
    private val processor by lazy { SubscriptionProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val subscriptionToUpdate = Subscription(
            id = SubscriptionRequestId("123"),
        )
        val ctx = Context(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            subscriptionRequest = subscriptionToUpdate,
        )
        processor.exec(ctx)
        assertEquals(State.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initAd.id, ctx.subscriptionResponse.id)
        assertEquals(initAd.title, ctx.subscriptionResponse.title)
        assertEquals(initAd.description, ctx.subscriptionResponse.description)
        assertEquals(initAd.subscriptionType, ctx.subscriptionResponse.subscriptionType)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}