package ru.otus.otuskotlin.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class UserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = UserId("")
    }
}