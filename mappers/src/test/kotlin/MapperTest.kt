import models.*
import org.junit.Test
import org.otus.otuskotlin.api.v1.models.*
import ru.otus.otuskotlin.mappers.fromSubscriptionTransport
import ru.otus.otuskotlin.mappers.toTransportSubscription
import stubs.Stubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = SubscriptionCreateRequest(
            requestId = "1234",
            debug = SubscriptionDebug(
                mode = SubscriptionRequestDebugMode.STUB,
                stub = SubscriptionRequestDebugStubs.SUCCESS,
            ),
            subscription = SubscriptionCreateObject(
                title = "title",
                description = "desc",
            ),
        )

        val context = Context()
        context.fromSubscriptionTransport(req)

        assertEquals(Stubs.SUCCESS, context.stubCase)
        assertEquals(WorkMode.STUB, context.workMode)
        assertEquals("title", context.subscriptionRequest.title)
    }

    @Test
    fun toTransport() {
        val context = Context(
            subscriptionRequestId = SubscriptionRequestId("1234"),
            command = Command.CREATE,
            subscriptionResponse = Subscription(
                title = "title",
                description = "desc",
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

        val req = context.toTransportSubscription() as SubscriptionCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("title", req.subscription?.title)
        assertEquals("desc", req.subscription?.description)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
