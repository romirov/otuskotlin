package ru.otus.otuskotlin.repo.inmemory

import ru.otus.otuskotlin.repo.tests.RepoSubscriptionSearchTest

class SubscriptionRepoInMemorySearchTest : RepoSubscriptionSearchTest() {
    override val repo = SubscriptionRepoInMemory(
        initObjects = initObjects
    )
}