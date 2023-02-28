package models

import kotlin.jvm.JvmInline

@JvmInline
value class SubscriptionId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = SubscriptionId("")
    }
}
