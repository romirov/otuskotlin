import kotlinx.datetime.Clock
import models.*
import org.otus.otuskotlin.api.logs.models.*

fun Context.toSubscriptionLog(logId: String) = CommonSubscriptionLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "otuskotlin",
    subscription = toSubscriptionLog(),
    errors = errors.map { it.toSubscriptionLog() },
)

fun Context.toSubscriptionLog(): SubscriptionLogModel? {
    val subscriptionNone = Subscription()
    return SubscriptionLogModel(
        requestId = requestId.takeIf { it != RequestId.NONE }?.asString(),
        requestSubscription = subscriptionRequest.takeIf { it != subscriptionNone }?.toSubscriptionLog(),
        responseSubscription = subscriptionResponse.takeIf { it != subscriptionNone }?.toSubscriptionLog(),
        responseSubscriptions = subscriptionsResponse.takeIf { it.isNotEmpty() }?.filter { it != subscriptionNone }?.map { it.toSubscriptionLog() },
        requestFilter = subscriptionFilterRequest.takeIf { it != Filter() }?.toSubscriptionLog(),
    ).takeIf { it != SubscriptionLogModel() }
}

private fun Filter.toSubscriptionLog() = SubscriptionFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    statusString = statusString.takeIf { it != SubscriptionStatus.NONE }?.toString(),
    dealSide = dealSide.takeIf { it != DealSide.NONE }?.name,
)

fun CommonError.toSubscriptionLog() = SubscriptionErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun Subscription.toSubscriptionLog() = SubscriptionLog(
    id = id.takeIf { it != SubscriptionId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    subscriptionType = subscriptionType.takeIf { it != DealSide.NONE }?.name,
    subscriptionStatus = subscriptionStatus.takeIf { it != SubscriptionStatus.NONE }?.name,
    productId = productId.takeIf { it != ProductId.NONE }?.asString(),
)
