package com.radenyaqien.core.data.remote.model


import com.squareup.moshi.Json

data class SearchUserResponse(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    @Json(name = "items")
    val items: List<UsersItem>,
    @Json(name = "total_count")
    val totalCount: Int
)