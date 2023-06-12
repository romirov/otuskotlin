package ru.otus.otuskotlin.repo.inmemory

import ru.otus.otuskotlin.repo.tests.RepoSubscriptionUpdateTest

class SubscriptionRepoInMemoryUpdateTest : RepoSubscriptionUpdateTest() {
    override val repo = SubscriptionRepoInMemory(
        initObjects = initObjects,
    )
}