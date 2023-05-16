package models

import kotlin.jvm.JvmInline

@JvmInline
value class SubscriptionRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = SubscriptionRequestId("")
    }
}
