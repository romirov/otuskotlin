import models.*
import org.junit.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromContext() {
        val context = Context(
            subscriptionRequestId = SubscriptionRequestId("1234"),
            command = Command.CREATE,
            subscriptionResponse = Subscription(
                title = "title",
                description = "desc",
                subscriptionType = DealSide.DEMAND,
            ),
            errors = mutableListOf(
                CommonError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = State.RUNNING,
        )

        val log = context.toSubscriptionLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("ok-marketplace", log.source)
        assertEquals("1234", log.subscription?.requestId)
        val error = log.errors?.firstOrNull()
        assertEquals("wrong title", error?.message)
        assertEquals("ERROR", error?.level)
    }
}