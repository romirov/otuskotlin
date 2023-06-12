package ru.otus.otuskotlin.repo.inmemory

import ru.otus.otuskotlin.repo.tests.RepoSubscriptionDeleteTest


class SubscriptionRepoInMemoryDeleteTest : RepoSubscriptionDeleteTest() {
    override val repo = SubscriptionRepoInMemory(
        initObjects = initObjects
    )
}