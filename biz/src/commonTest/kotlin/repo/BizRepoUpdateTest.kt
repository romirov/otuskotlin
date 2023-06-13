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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = UserId("321")
    private val command = Command.UPDATE
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
        },
        invokeUpdateSubscription = {
            DbSubscriptionResponse(
                isSuccess = true,
                data = Subscription(
                    id = SubscriptionRequestId("123"),
                    title = "xyz",
                    description = "xyz",
                    subscriptionType = DealSide.DEMAND,
                )
            )
        }
    ) }
    private val settings by lazy {
        CorSettings(
            repoSubscriptionTest = repo
        )
    }
    private val processor by lazy { SubscriptionProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = Subscription(
            id = SubscriptionRequestId("123"),
            title = "xyz",
            description = "xyz",
            subscriptionType = DealSide.DEMAND,
        )
        val ctx = Context(
            command = command,
            state = State.NONE,
            workMode = WorkMode.TEST,
            subscriptionRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(State.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.subscriptionResponse.id)
        assertEquals(adToUpdate.title, ctx.subscriptionResponse.title)
        assertEquals(adToUpdate.description, ctx.subscriptionResponse.description)
        assertEquals(adToUpdate.subscriptionType, ctx.subscriptionResponse.subscriptionType)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}