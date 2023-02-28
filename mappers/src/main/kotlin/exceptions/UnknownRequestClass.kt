package ru.otus.otuskotlin.mappers.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to SubscriptionContext")