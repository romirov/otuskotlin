package helpers

import Context
import models.CommonError
import models.State

fun Throwable.asCommonError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = CommonError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun Context.addError(vararg error: CommonError) = errors.addAll(error)

fun Context.fail(error: CommonError) {
    addError(error)
    state = State.FAILING
}
fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: CommonError.Level = CommonError.Level.ERROR,
) = CommonError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: CommonError.Level = CommonError.Level.ERROR,
) = CommonError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)