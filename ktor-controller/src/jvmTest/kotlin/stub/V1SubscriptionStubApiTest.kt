package ru.otus.otuskotlin.app.stub

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
import ru.otus.otuskotlin.app.moduleJvm
import ru.otus.otuskotlin.lib.logging.common.SLogWrapper
import kotlin.test.assertEquals

class V1SubscriptionStubApiTest {
    private val logger = SLogWrapper.DEFAULT
    @Test
    fun create() = testApplication {
        application {
            moduleJvm()
        }
        val client = myClient()

        val response = client.post("/v1/subscription/create") {
            val requestObj = SubscriptionCreateRequest(
                requestId = "11",
                subscription = SubscriptionCreateObject(
                    title = "PostgreSQL cloud service",
                    description = "PostgreSQL cloud database service",
                    subscriptionType = DealSide.DEMAND,
                    status = SubscriptionStatus.ACTIVE,
                ),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.STUB,
                    stub = SubscriptionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionCreateResponse>()
        logger.doWithLogging { responseObj }
        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.subscription?.id)
    }

    @Test
    fun read() = testApplication {
        application {
            moduleJvm()
        }
        val client = myClient()

        val response = client.post("/v1/subscription/read") {
            val requestObj = SubscriptionReadRequest(
                requestId = "11",
                subscription = SubscriptionReadObject("1"),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.STUB,
                    stub = SubscriptionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.subscription?.id)
    }

    @Test
    fun update() = testApplication {
        application {
            moduleJvm()
        }
        val client = myClient()

        val response = client.post("/v1/subscription/update") {
            val requestObj = SubscriptionUpdateRequest(
                requestId = "11",
                subscription = SubscriptionUpdateObject(
                    id = "1",
                    title = "PostgreSQL cloud service",
                    description = "PostgreSQL cloud database service",
                    subscriptionType = DealSide.DEMAND,
                    status = SubscriptionStatus.ACTIVE,
                ),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.STUB,
                    stub = SubscriptionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.subscription?.id)
    }

    @Test
    fun delete() = testApplication {
        application {
            moduleJvm()
        }
        val client = myClient()

        val response = client.post("/v1/subscription/delete") {
            val requestObj = SubscriptionDeleteRequest(
                requestId = "11",
                subscription = SubscriptionDeleteObject(
                    id = "1",
                ),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.STUB,
                    stub = SubscriptionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.subscription?.id)
    }

    @Test
    fun search() = testApplication {
        application {
            moduleJvm()
        }
        val client = myClient()

        val response = client.post("/v1/subscription/search") {
            val requestObj = SubscriptionSearchRequest(
                requestId = "11",
                subscriptionFilter = SubscriptionSearchFilter(),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.STUB,
                    stub = SubscriptionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionSearchResponse>()
        assertEquals(200, response.status.value)
        val p = responseObj.subscriptions?.first()?.id
        logger.info(p.toString())
        assertEquals("d-1-01", p)
    }

    @Test
    fun status() = testApplication {
        application {
            moduleJvm()
        }
        val client = myClient()

        val response = client.post("/v1/subscription/status") {
            val requestObj = SubscriptionStatusRequest(
                requestId = "11",
                subscription = SubscriptionStatusObject(
                    id = "11"
                ),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.STUB,
                    stub = SubscriptionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionStatusResponse>()
        assertEquals(200, response.status.value)
        assertEquals("11", responseObj.subscription?.id)
    }

    @Test
    fun offers() = testApplication {
        application {
            moduleJvm()
        }
        val client = myClient()

        val response = client.post("/v1/subscription/offers") {
            val requestObj = SubscriptionOffersRequest(
                requestId = "11",
                subscription = SubscriptionReadObject(
                    id = "1",
                ),
                debug = SubscriptionDebug(
                    mode = SubscriptionRequestDebugMode.STUB,
                    stub = SubscriptionRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<SubscriptionOffersResponse>()
        assertEquals(200, response.status.value)
        assertEquals("s-1-01", responseObj.subscriptions?.first()?.id)
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
