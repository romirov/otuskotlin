package ru.otus.otuskotlin.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.CommonDealSide
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.repo.DbSubscriptionRequest
import ru.otus.otuskotlin.common.repo.ISubscriptionRepository
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoSubscriptionCreateTest {
    abstract val repo: ISubscriptionRepository

    private val createObj = Subscription(
        title = "create object",
        description = "create object description",
        subscriptionType = CommonDealSide.SUPPLY,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createSubscription(DbSubscriptionRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: SubscriptionRequestId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.subscriptionType, result.data?.subscriptionType)
        assertNotEquals(SubscriptionRequestId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitSubscriptions("create") {
        override val initObjects: List<Subscription> = emptyList()
    }
}
