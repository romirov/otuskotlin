import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.payment() {
    route("payment") {
        post("create") {
            call.createPayment()
        }
        post("status") {
            call.statusPayment()
        }
    }
}