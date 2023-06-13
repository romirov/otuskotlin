package ru.otus.otuskotlin.biz

import ru.otus.otuskotlin.biz.general.initPaymentRepo
import ru.otus.otuskotlin.biz.general.prepareResultPayment
import ru.otus.otuskotlin.biz.general.prepareResultSubscription
import ru.otus.otuskotlin.biz.groups.operation
import ru.otus.otuskotlin.biz.groups.stubs
import ru.otus.otuskotlin.biz.repo.*
import ru.otus.otuskotlin.biz.validation.*
import ru.otus.otuskotlin.biz.workers.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.PaymentRequestId
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.lib.cor.chain
import ru.otus.otuskotlin.lib.cor.rootChain
import ru.otus.otuskotlin.lib.cor.worker
import ru.otus.otuskotlin.stubs.PaymentStub
import stubReadSuccess


class PaymentProcessor(private val settings: CorSettings = CorSettings()) {
    suspend fun exec(ctx: Context) = BusinessChain.exec(ctx.apply { settings =  this@PaymentProcessor.settings})

    companion object {
        private val BusinessChain = rootChain<Context> {
            initStatus("Инициализация статуса")
            initPaymentRepo("Инициализация репозитория")

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
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreatePayment("Подготовка объекта для сохранения")
                    repoCreatePayment("Создание объявления в БД")
                }
                prepareResultPayment("Подготовка ответа")
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
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                repoSearchPayment("Поиск платежа в БД по фильтру")
                prepareResultPayment("Подготовка ответа")
            }
        }.build()
    }
}