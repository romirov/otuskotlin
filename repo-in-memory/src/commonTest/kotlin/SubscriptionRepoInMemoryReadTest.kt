package ru.otus.otuskotlin.repo.inmemory

import ru.otus.otuskotlin.repo.tests.RepoSubscriptionReadTest

class SubscriptionRepoInMemoryReadTest : RepoSubscriptionReadTest() {
    override val repo = SubscriptionRepoInMemory(
        initObjects = initObjects
    )
}