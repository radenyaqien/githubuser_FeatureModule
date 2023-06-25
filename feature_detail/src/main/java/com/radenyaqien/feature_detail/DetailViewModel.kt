package com.radenyaqien.feature_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radenyaqien.core.Resource
import com.radenyaqien.core.domain.model.DetailUser
import com.radenyaqien.core.domain.usecase.GetDetailUserUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getDetailUserUsecase: GetDetailUserUsecase,
    username: String
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state get() = _state.asStateFlow()

    private fun getDetailUser(username: String) {
        viewModelScope.launch {
            getDetailUserUsecase(username).collect {
                when (it) {
                    is Resource.Error -> onError(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> onSuccess(it.data)
                }
            }
        }

    }

    private fun onSuccess(detailUser: DetailUser?) {
        _state.update {
            it.copy(
                data = detailUser,
                isLoading = false
            )
        }
    }

    private fun onLoading() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun onError(message: String?) {
        _state.update {
            it.copy(
                message = message,
                isLoading = false
            )
        }
    }

    fun clearErrorMessage() {
        _state.update {
            it.copy(
                message = null
            )
        }
    }

    init {


        Log.d("DetailViewModel", "username: $username")
        getDetailUser(username)
    }
}