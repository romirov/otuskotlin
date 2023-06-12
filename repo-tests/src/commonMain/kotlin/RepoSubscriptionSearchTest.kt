package ru.otus.otuskotlin.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.otus.otuskotlin.common.Subscription
import ru.otus.otuskotlin.common.models.DealSide
import ru.otus.otuskotlin.common.repo.DbSubscriptionFilterRequest
import ru.otus.otuskotlin.common.repo.ISubscriptionRepository
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoSubscriptionSearchTest {
    abstract val repo: ISubscriptionRepository

    protected open val initializedObjects: List<Subscription> = initObjects

    @Test
    fun searchDealSide() = runRepoTest {
        val result = repo.searchSubscription(DbSubscriptionFilterRequest(dealSide = DealSide.SUPPLY))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitSubscriptions("search") {
        override val initObjects: List<Subscription> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2"),
            createInitTestModel("ad3", subscriptionType = DealSide.SUPPLY),
            createInitTestModel("ad4"),
            createInitTestModel("ad5", subscriptionType = DealSide.SUPPLY),
        )
    }
}
