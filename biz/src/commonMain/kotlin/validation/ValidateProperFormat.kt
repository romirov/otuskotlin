package ru.otus.otuskotlin.biz.validation

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.helpers.errorValidation
import ru.otus.otuskotlin.common.helpers.fail
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в MkplAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { subscriptionValidating.id != SubscriptionRequestId.NONE && ! subscriptionValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = subscriptionValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}