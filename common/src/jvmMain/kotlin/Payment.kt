import models.PaymentRequestId
import models.PaymentStatus
import models.SubscriptionRequestId
import models.UserId
import java.math.BigDecimal

data class Payment(
    var id: PaymentRequestId = PaymentRequestId.NONE,
    var subscriptionID: SubscriptionRequestId = SubscriptionRequestId.NONE,
    var title: String = "",
    var description: String = "",
    var price: BigDecimal = 0.0.toBigDecimal(),
    var ownerId: UserId = UserId.NONE,
    val paymentStatus: PaymentStatus = PaymentStatus.NONE
)