package com.radenyaqien.githubuser2023.home

import com.radenyaqien.core.domain.model.GithubUser

data class HomeUiState(
    val data: List<GithubUser> = emptyList(),
    val message: String? = null,
    val isLoading: Boolean = false
)
