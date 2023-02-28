package ru.otus.otuskotlin.mappers.exceptions

import models.Command

class UnknownSubscriptionCommand(command: Command) : Throwable("Wrong command $command at mapping toTransport stage")