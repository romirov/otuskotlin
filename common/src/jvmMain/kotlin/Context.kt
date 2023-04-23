import kotlinx.datetime.Instant
import models.*
import stubs.Stubs

data class Context(
    var command: Command = Command.NONE,
    var state: State = State.NONE,
    val errors: MutableList<CommonError> = mutableListOf(),
    var settings: CorSettings = CorSettings.NONE,

    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: Stubs = Stubs.NONE,

    var requestId: RequestId = RequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var subscriptionRequest: Subscription = Subscription(),
    var subscriptionFilterRequest: Filter = Filter(),
    var subscriptionResponse: Subscription = Subscription(),
    var subscriptionsResponse: MutableList<Subscription> = mutableListOf(),

    var paymentRequest: Payment = Payment(),
    var paymentResponse: Payment = Payment(),
    var paymentsResponse: MutableList<Payment> = mutableListOf(),
)