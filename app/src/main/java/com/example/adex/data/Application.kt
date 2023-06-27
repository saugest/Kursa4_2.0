package com.example.adex.data

data class Application(
    val id: Int,
    val title: String,
    val description: String,
    val subscribes: Int,
    val views: Int,
    val rating: Double,
    val er: Double,
    val price: Double,
    val user_id: Int,
    val currentUserName: String,
    val currentUserId: Int,
    val currentUserLogin: String,
    val titleApplicationRecipient: String
    )
