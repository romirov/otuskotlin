package ru.otus.otuskotlin.biz

import ru.otus.otuskotlin.biz.groups.operation
import ru.otus.otuskotlin.biz.groups.stubs
import ru.otus.otuskotlin.biz.validation.*
import ru.otus.otuskotlin.biz.workers.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.PaymentRequestId
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.lib.cor.rootChain
import ru.otus.otuskotlin.lib.cor.worker
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
                validation {
                    worker("Копируем поля в paymentValidating") { paymentValidating = paymentRequest.deepCopy() }
                    worker("Очистка id") { paymentValidating.id = PaymentRequestId.NONE }
                    worker("Очистка заголовка") { paymentValidating.title = paymentValidating.title.trim() }
                    worker("Очистка описания") { paymentValidating.description = paymentValidating.description.trim() }
                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
                    validateTitleHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")

                    finishAdValidation("Завершение проверок")
                }
            }
            operation("Получение статуса платежа", Command.STATUS) {
                stubs("Обработка стабов") {
                    stubStatusSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionValidating") { paymentValidating = paymentRequest.deepCopy() }
                    worker("Очистка id") { paymentValidating.id = PaymentRequestId(paymentValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}