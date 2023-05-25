package ru.otus.otuskotlin.mappers.exceptions

import ru.otus.otuskotlin.common.models.Command

class UnknownSubscriptionCommand(command: Command) : Throwable("Wrong command $command at mapping toTransport stage")