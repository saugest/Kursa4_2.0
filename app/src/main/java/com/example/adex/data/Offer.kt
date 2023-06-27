package com.example.adex.data

data class Offer(
    val id: Int,
    val account_consumer: Int,
    val account_recipient: Int,
    val application_consumer: Int,
    val application_recipient: Int,
    val description: String,
    val positive_responce: Boolean,
    val negative_responce: Boolean,
    val application_consumer_title: String,
    val title: String
)
