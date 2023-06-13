package ru.otus.otuskotlin.biz

import ru.otus.otuskotlin.biz.general.initPaymentRepo
import ru.otus.otuskotlin.biz.general.initSubscriptionRepo
import ru.otus.otuskotlin.biz.general.prepareResultSubscription
import ru.otus.otuskotlin.biz.groups.operation
import ru.otus.otuskotlin.biz.groups.stubs
import ru.otus.otuskotlin.biz.repo.*
import ru.otus.otuskotlin.biz.validation.*
import ru.otus.otuskotlin.biz.workers.*
import ru.otus.otuskotlin.common.Context
import ru.otus.otuskotlin.common.CorSettings
import ru.otus.otuskotlin.common.models.Command
import ru.otus.otuskotlin.common.models.State
import ru.otus.otuskotlin.common.models.SubscriptionRequestId
import ru.otus.otuskotlin.lib.cor.chain
import ru.otus.otuskotlin.lib.cor.rootChain
import ru.otus.otuskotlin.lib.cor.worker
import stubReadSuccess


class SubscriptionProcessor(private val settings: CorSettings = CorSettings()) {
    suspend fun exec(ctx: Context) = BusinessChain.exec(ctx.apply { settings =  this@SubscriptionProcessor.settings})

    companion object {
        private val BusinessChain = rootChain<Context> {
            initStatus("Инициализация статуса")
            initSubscriptionRepo("Инициализация репозитория")

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
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreateSubscription("Подготовка объекта для сохранения")
                    repoCreateSubscription("Создание объявления в БД")
                }
                prepareResultSubscription("Подготовка ответа")
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
                chain {
                    title = "Логика чтения"
                    repoReadSubscription("Чтение подписки из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == State.RUNNING }
                        handle { subscriptionRepoDone = subscriptionRepoRead }
                    }
                }
                prepareResultSubscription("Подготовка ответа")
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
                chain {
                    title = "Логика сохранения"
                    repoReadSubscription("Чтение подписки из БД")
                    repoPrepareUpdateSubscription("Подготовка объекта для обновления")
                    repoUpdateSubscription("Обновление подписки в БД")
                }
                prepareResultSubscription("Подготовка ответа")
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
                chain {
                    title = "Логика удаления"
                    repoReadSubscription("Чтение подписки из БД")
                    repoPrepareDeleteSubscription("Подготовка объекта для удаления")
                    repoDeleteSubscription("Удаление подписки из БД")
                }
                prepareResultSubscription("Подготовка ответа")
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
                repoSearchSubscription("Поиск объявления в БД по фильтру")
                prepareResultSubscription("Подготовка ответа")
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
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                repoSearchStatusSubscription("Поиск подписки в БД по фильтру")
                prepareResultSubscription("Подготовка ответа")
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
                chain {
                    title = "Логика поиска в БД"
                    repoReadSubscription("Чтение подписки из БД")
                    repoPrepareOffersSubscription("Подготовка данных для поиска предложений")
                    repoOffersSubscription("Поиск предложений для подписки в БД")
                }
                prepareResultSubscription("Подготовка ответа")
            }
        }.build()
    }
}