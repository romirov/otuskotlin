package ru.otus.otuskotlin.common.repo

import ru.otus.otuskotlin.common.models.CommonError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<CommonError>
}