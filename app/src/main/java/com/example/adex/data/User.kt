package com.example.adex.data

data class User(
    val id: Int,
    val login: String,
    val password: String,
    val surname: String,
    val name: String,
    val executor: Boolean,
    val customer: Boolean
)
