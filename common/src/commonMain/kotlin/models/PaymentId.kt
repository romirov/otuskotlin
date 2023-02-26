package models

import kotlin.jvm.JvmInline
@JvmInline
value class PaymentId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = PaymentId("")
    }
}