package com.radenyaqien.core.domain.model

data class DetailUser(
    val id: Int,
    val username: String,
    val name: String,
    val avatarUrl: String,
    val bio: String,
    val email: String,
    val followers: Int,
    val following: Int,
)
