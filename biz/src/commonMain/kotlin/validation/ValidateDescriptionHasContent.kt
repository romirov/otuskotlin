package ru.otus.otuskotlin.biz.validation

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.helpers.errorValidation
import ru.otus.otuskotlin.common.helpers.fail
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { subscriptionValidating.description.isNotEmpty() && ! subscriptionValidating.description.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}