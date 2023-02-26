import kotlinx.datetime.Instant
import models.*
import stubs.Stubs
import kotlin.Error

data class MkplContext(
    var state: State = State.NONE,
    val errors: MutableList<Error> = mutableListOf(),

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: Stubs = Stubs.NONE,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var subscriptionRequest: Subscription = Subscription(),
    var subscriptionFilterRequest: Filter = Filter(),
    var subscriptionResponse: Subscription = Subscription(),
    var subscriptionsResponse: MutableList<Subscription> = mutableListOf(),

    var paymentRequest: Payment = Payment(),
    var paymentResponse: Payment = Payment()
)