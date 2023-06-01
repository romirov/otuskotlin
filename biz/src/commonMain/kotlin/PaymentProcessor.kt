package ru.otus.otuskotlin.biz

import ru.otus.otuskotlin.biz.groups.operation
import ru.otus.otuskotlin.biz.groups.stubs
import ru.otus.otuskotlin.biz.workers.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.lib.cor.rootChain
import ru.otus.otuskotlin.stubs.PaymentStub
import stubReadSuccess


class PaymentProcessor {
    suspend fun exec(ctx: Context) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<Context> {
            initStatus("Инициализация статуса")

            operation("Создание платежа", Command.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Статус платежа", Command.STATUS) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
        }.build()
    }
}