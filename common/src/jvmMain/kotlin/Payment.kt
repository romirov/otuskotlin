import models.PaymentId
import models.PaymentStatus
import models.SubscriptionId
import models.UserId
import java.math.BigDecimal

data class Payment(
    var id: PaymentId = PaymentId.NONE,
    var subscriptionID: SubscriptionId = SubscriptionId.NONE,
    var title: String = "",
    var description: String = "",
    var price: BigDecimal = 0.0.toBigDecimal(),
    var ownerId: UserId = UserId.NONE,
    val paymentStatus: PaymentStatus = PaymentStatus.NONE
)