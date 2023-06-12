package ru.otus.otuskotlin.biz.general

import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.helpers.errorAdministration
import ru.otus.otuskotlin.common.helpers.fail
import ru.otus.otuskotlin.common.models.WorkMode
import ru.otus.otuskotlin.common.repo.IPaymentRepository
import ru.otus.otuskotlin.common.repo.ISubscriptionRepository
import ru.otus.otuskotlin.lib.cor.ICorChainDsl
import ru.otus.otuskotlin.lib.cor.worker

fun ICorChainDsl<Context>.initSubscriptionRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        subscriptionRepo = when {
            workMode == WorkMode.TEST -> settings.repoSubscriptionTest
            workMode == WorkMode.STUB -> settings.repoSubscriptionStub
            else -> settings.repoSubscriptionProd
        }
        if (workMode != WorkMode.STUB && subscriptionRepo == ISubscriptionRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}

fun ICorChainDsl<Context>.initPaymentRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        paymentRepo = when {
            workMode == WorkMode.TEST -> settings.repoPaymentTest
            workMode == WorkMode.STUB -> settings.repoPaymentStub
            else -> settings.repoPaymentProd
        }
        if (workMode != WorkMode.STUB && paymentRepo == IPaymentRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
