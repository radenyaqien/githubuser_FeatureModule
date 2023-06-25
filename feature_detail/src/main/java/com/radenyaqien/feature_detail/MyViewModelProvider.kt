package com.radenyaqien.feature_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.radenyaqien.core.domain.usecase.GetDetailUserUsecase

@Suppress("UNCHECKED_CAST")
class MyViewModelProvider(
    private val usecase: GetDetailUserUsecase,
    private val username: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(getDetailUserUsecase = usecase, username) as T
    }
}