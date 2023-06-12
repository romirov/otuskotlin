package ru.otus.otuskotlin.repo.inmemory

import ru.otus.otuskotlin.repo.tests.RepoSubscriptionCreateTest

class SubscriptionRepoInMemoryCreateTest : RepoSubscriptionCreateTest() {
    override val repo = SubscriptionRepoInMemory(
        initObjects = initObjects,
    )
}