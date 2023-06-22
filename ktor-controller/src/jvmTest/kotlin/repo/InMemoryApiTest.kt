package ru.otus.otuskotlin.app.repo

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import org.otus.otuskotlin.api.v1.models.*

import ru.otus.otuskotlin.app.AppSettings
import ru.otus.otuskotlin.app.moduleJvm
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.common.models.CommonDealSide
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.repo.inmemory.SubscriptionRepoInMemory
import ru.otus.otuskotlin.stubs.SubscriptionStub
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

typealias commonModelsDealSide = ru.otus.otuskotlin.common.models.CommonDealSide

class V1AdInmemoryApiTest {
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidSup = "10000000-0000-0000-0000-000000000003"
    private val initAd = SubscriptionStub.prepareResult {
        id = SubscriptionRequestId(uuidOld)
        title = "abc"
        description = "abc"
        subscriptionType = CommonDealSide.DEMAND
    }
    private val initAdSupply = SubscriptionStub.prepareResult {
        id = SubscriptionRequestId(uuidSup)
        title = "abc"
        description = "abc"
        subscriptionType = CommonDealSide.SUPPLY
    }

    @Test
    fun create() = testApplication {
        val repo = SubscriptionRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
        application {
            moduleJvm(AppSettings(corSettings = CorSettings(repoSubscriptionTest = repo)))
        }
        val client = myClient()

        val createSubscription = SubscriptionCreateObject(
            title = "PostgreSQL cloud service",
            description = "PostgreSQL cloud database service",
            subscriptionType = DealSide.DEMAND
        )

        val response = client.post("/v1/subscription/create") {
            val requestObj = SubscriptionCreateRequest(
                requestId = "12345",
                subscription = createSubscription,
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidNew, responseObj.subscription?.id)
        assertEquals(createSubscription.title, responseObj.subscription?.title)
        assertEquals(createSubscription.description, responseObj.subscription?.description)
        assertEquals(createSubscription.subscriptionType, responseObj.subscription?.subscriptionType)
    }

    @Test
    fun read() = testApplication {
        val repo = SubscriptionRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
        application {
            moduleJvm(AppSettings(corSettings = CorSettings(repoSubscriptionTest = repo)))
        }
        val client = myClient()

        val response = client.post("/v1/subscription/read") {
            val requestObj = SubscriptionReadRequest(
                requestId = "12345",
                subscription = SubscriptionReadObject(uuidOld),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.subscription?.id)
    }

    @Test
    fun update() = testApplication {
        val repo = SubscriptionRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
        application {
            moduleJvm(AppSettings(corSettings = CorSettings(repoSubscriptionTest = repo)))
        }
        val client = myClient()

        val adUpdate = SubscriptionUpdateObject(
            id = uuidOld,
            title = "PostgreSQL cloud service",
            description = "PostgreSQL cloud database service",
            subscriptionType = DealSide.DEMAND,
        )

        val response = client.post("/v1/subscription/update") {
            val requestObj = SubscriptionUpdateRequest(
                requestId = "12345",
                subscription = adUpdate,
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(adUpdate.id, responseObj.subscription?.id)
        assertEquals(adUpdate.title, responseObj.subscription?.title)
        assertEquals(adUpdate.description, responseObj.subscription?.description)
        assertEquals(adUpdate.subscriptionType, responseObj.subscription?.subscriptionType)
    }

    @Test
    fun delete() = testApplication {
        val repo = SubscriptionRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
        application {
            moduleJvm(AppSettings(corSettings = CorSettings(repoSubscriptionTest = repo)))
        }
        val client = myClient()

        val response = client.post("/v1/subscription/delete") {
            val requestObj = SubscriptionDeleteRequest(
                requestId = "12345",
                subscription = SubscriptionDeleteObject(
                    id = uuidOld,
                ),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.subscription?.id)
    }

    @Test
    fun search() = testApplication {
        val repo = SubscriptionRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew })
        application {
            moduleJvm(AppSettings(corSettings = CorSettings(repoSubscriptionTest = repo)))
        }
        val client = myClient()

        val response = client.post("/v1/subscription/search") {
            val requestObj = SubscriptionSearchRequest(
                requestId = "12345",
                subscriptionFilter = SubscriptionSearchFilter(),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.subscriptions?.size)
        assertEquals(uuidOld, responseObj.subscriptions?.first()?.id)
    }

    @Test
    fun offers() = testApplication {
        val repo = SubscriptionRepoInMemory(initObjects = listOf(initAd, initAdSupply), randomUuid = { uuidNew })
        application {
            moduleJvm(AppSettings(corSettings = CorSettings(repoSubscriptionTest = repo)))
        }
        val client = myClient()

        val response = client.post("/v1/subscription/offers") {
            val requestObj = SubscriptionOffersRequest(
                requestId = "12345",
                subscription = SubscriptionReadObject(
                    id = uuidOld,
                ),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionOffersResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.subscriptions?.size)
        assertEquals(uuidSup, responseObj.subscriptions?.first()?.id)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }

}