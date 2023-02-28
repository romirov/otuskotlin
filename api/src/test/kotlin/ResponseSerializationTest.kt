
import org.junit.Test
import org.otus.otuskotlin.api.v1.models.SubscriptionCreateResponse
import org.otus.otuskotlin.api.v1.models.SubscriptionResponse
import org.otus.otuskotlin.api.v1.models.SubscriptionResponseObject
import ru.otus.otuskotlin.api.apiV1Mapper
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {

    private val response = SubscriptionCreateResponse(
        requestId = "123",
        subscription = SubscriptionResponseObject(
            title = "subscription title",
            description = "subscription description"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"subscription title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, SubscriptionResponse::class.java) as SubscriptionCreateResponse

        assertEquals(response, obj)
    }
}