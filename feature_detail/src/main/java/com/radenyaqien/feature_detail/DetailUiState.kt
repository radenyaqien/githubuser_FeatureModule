package com.radenyaqien.feature_detail

import com.radenyaqien.core.domain.model.DetailUser

data class DetailUiState(
    val data: DetailUser? = null,
    val message: String? = null,
    val isLoading: Boolean = false
)
