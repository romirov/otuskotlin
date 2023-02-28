import org.junit.Test
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.api.apiV1Mapper
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = SubscriptionCreateRequest(
        requestId = "123",
        debug = SubscriptionDebug(
            mode = SubscriptionRequestDebugMode.STUB,
            stub = SubscriptionRequestDebugStubs.BAD_TITLE
        ),
        subscription = SubscriptionCreateObject(
            title = "subscription title",
            description = "subscription description"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"subscription title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, SubscriptionRequest::class.java) as SubscriptionCreateRequest

        assertEquals(request, obj)
    }
}