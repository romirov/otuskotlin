package models

enum class Command {
    NONE,
    CREATE,
    READ,
    UPDATE,
    DELETE,
    SEARCH,
    STATUS,
    OFFERS,
    PAY,
    ROLLBACK
}