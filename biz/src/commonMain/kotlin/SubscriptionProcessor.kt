package ru.otus.otuskotlin.biz

import ru.otus.otuskotlin.biz.groups.operation
import ru.otus.otuskotlin.biz.groups.stubs
import ru.otus.otuskotlin.biz.validation.*
import ru.otus.otuskotlin.biz.workers.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.lib.cor.rootChain
import ru.otus.otuskotlin.lib.cor.worker
import stubReadSuccess


class SubscriptionProcessor {
    suspend fun exec(ctx: Context) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<Context> {
            initStatus("Инициализация статуса")

            operation("Создание подписки", Command.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionValidating") { subscriptionValidating = subscriptionRequest.deepCopy() }
                    worker("Очистка id") { subscriptionValidating.id = SubscriptionRequestId.NONE }
                    worker("Очистка заголовка") { subscriptionValidating.title = subscriptionValidating.title.trim() }
                    worker("Очистка описания") { subscriptionValidating.description = subscriptionValidating.description.trim() }
                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
                    validateTitleHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")

                    finishAdValidation("Завершение проверок")
                }
            }
            operation("Получить подписку", Command.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionValidating") { subscriptionValidating = subscriptionRequest.deepCopy() }
                    worker("Очистка id") { subscriptionValidating.id = SubscriptionRequestId(subscriptionValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменить подписку", Command.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionValidating") { subscriptionValidating = subscriptionRequest.deepCopy() }
                    worker("Очистка id") { subscriptionValidating.id = SubscriptionRequestId(subscriptionValidating.id.asString().trim()) }
                    worker("Очистка заголовка") { subscriptionValidating.title = subscriptionValidating.title.trim() }
                    worker("Очистка описания") { subscriptionValidating.description = subscriptionValidating.description.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Удалить подписку", Command.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionValidating") {
                        subscriptionValidating = subscriptionRequest.deepCopy() }
                    worker("Очистка id") { subscriptionValidating.id = SubscriptionRequestId(subscriptionValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск подписки", Command.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionFilterValidating") { subscriptionFilterValidating = subscriptionFilterRequest.copy() }

                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }

            }
            operation("Поиск подходящих предложений для подписки", Command.OFFERS) {
                stubs("Обработка стабов") {
                    stubOffersSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionValidating") { subscriptionValidating = subscriptionRequest.deepCopy() }
                    worker("Очистка id") { subscriptionValidating.id = SubscriptionRequestId(subscriptionValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Получение статуса подписки", Command.STATUS) {
                stubs("Обработка стабов") {
                    stubStatusSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в subscriptionValidating") { subscriptionValidating = subscriptionRequest.deepCopy() }
                    worker("Очистка id") { subscriptionValidating.id = SubscriptionRequestId(subscriptionValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}