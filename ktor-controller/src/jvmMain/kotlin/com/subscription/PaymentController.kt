package com.subscription

import Context
import PaymentStub
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.otus.otuskotlin.api.v1.models.PaymentCreateRequest
import org.otus.otuskotlin.api.v1.models.PaymentStatusRequest
import ru.otus.otuskotlin.mappers.fromPaymentTransport
import ru.otus.otuskotlin.mappers.toTransportPaymentCreate
import ru.otus.otuskotlin.mappers.toTransportPaymentStatus

suspend fun ApplicationCall.createPayment() {
    val request = receive<PaymentCreateRequest>()
    val context = Context()
    context.fromPaymentTransport(request)
    context.paymentResponse = PaymentStub.get()
    respond(context.toTransportPaymentCreate())
}

suspend fun ApplicationCall.statusPayment() {
    val request = receive<PaymentStatusRequest>()
    val context = Context()
    context.fromPaymentTransport(request)
    context.paymentResponse = PaymentStub.get()
    respond(context.toTransportPaymentStatus())
}