package ru.otus.otuskotlin.common.models

enum class Command {
    NONE,
    CREATE,
    READ,
    UPDATE,
    DELETE,
    SEARCH,
    STATUS,
    OFFERS,
    ROLLBACK
}