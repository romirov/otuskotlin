package ru.otus.otuskotlin.api

import com.fasterxml.jackson.databind.ObjectMapper
import jdk.jshell.JShell.Subscription
import org.otus.otuskotlin.api.v1.models.PaymentRequest
import org.otus.otuskotlin.api.v1.models.PaymentResponse
import org.otus.otuskotlin.api.v1.models.SubscriptionRequest
import org.otus.otuskotlin.api.v1.models.SubscriptionResponse

val apiV1Mapper = ObjectMapper()

fun apiV1RequestSerialize(request: SubscriptionRequest): String = apiV1Mapper.writeValueAsString(request)
fun apiV1RequestSerialize(request: PaymentRequest): String = apiV1Mapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : SubscriptionRequest> apiV1RequestSubscriptionDeserialize(json: String): T =
    apiV1Mapper.readValue(json, SubscriptionRequest::class.java) as T

@Suppress("UNCHECKED_CAST")
fun <T : PaymentRequest> apiV1RequestPaymentDeserialize(json: String): T =
    apiV1Mapper.readValue(json, PaymentRequest::class.java) as T

fun apiV1ResponseSerialize(response: SubscriptionResponse): String = apiV1Mapper.writeValueAsString(response)
fun apiV1ResponseSerialize(response: PaymentResponse): String = apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : SubscriptionResponse> apiV1ResponseSubscriptionDeserialize(json: String): T =
    apiV1Mapper.readValue(json, SubscriptionResponse::class.java) as T

@Suppress("UNCHECKED_CAST")
fun <T : PaymentResponse> apiV1ResponsePaymentDeserialize(json: String): T =
    apiV1Mapper.readValue(json, PaymentResponse::class.java) as T