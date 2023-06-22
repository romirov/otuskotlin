package ru.otus.otuskotlin.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.CommonDealSide
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.repo.DbSubscriptionRequest
import ru.otus.otuskotlin.common.repo.ISubscriptionRepository
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoSubscriptionUpdateTest {
    abstract val repo: ISubscriptionRepository
    protected open val updateSucc = initObjects[0]
    private val updateIdNotFound = SubscriptionRequestId("Subscription-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        Subscription(
            id = updateSucc.id,
            title = "update object",
            description = "update object description",
            subscriptionType = CommonDealSide.SUPPLY,
        )
    }
    private val reqUpdateNotFound = Subscription(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        subscriptionType = CommonDealSide.SUPPLY,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateSubscription(DbSubscriptionRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.title, result.data?.title)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.subscriptionType, result.data?.subscriptionType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateSubscription(DbSubscriptionRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitSubscriptions("update") {
        override val initObjects: List<Subscription> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
