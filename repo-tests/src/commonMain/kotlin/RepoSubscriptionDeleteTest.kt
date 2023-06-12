package ru.otus.otuskotlin.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.common.repo.DbSubscriptionIdRequest
import ru.otus.otuskotlin.common.repo.ISubscriptionRepository
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoSubscriptionDeleteTest {
    abstract val repo: ISubscriptionRepository
    protected open val deleteSucc = initObjects[0]

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteSubscription(DbSubscriptionIdRequest(deleteSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readSubscription(DbSubscriptionIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitSubscriptions("delete") {
        override val initObjects: List<Subscription> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val notFoundId = SubscriptionRequestId("Subscription-repo-delete-notFound")
    }
}
