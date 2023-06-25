package com.radenyaqien.githubuser2023.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radenyaqien.core.Resource
import com.radenyaqien.core.domain.model.GithubUser
import com.radenyaqien.core.domain.usecase.SearchUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchUsersUsecase: SearchUsersUsecase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state get() = _state.asStateFlow()

    fun searchUser(query: String) {
        viewModelScope.launch {
            searchUsersUsecase(query).collect { resource ->
                when (resource) {
                    is Resource.Error -> onError(resource.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> onSuccess(resource.data.orEmpty())
                }
            }
        }
    }

    private fun onSuccess(data: List<GithubUser>) {
        _state.update {
            it.copy(
                data = data,
                message = null,
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
        searchUser(
            URLDecoder.decode(
                "%20repos%3A%3E42%20followers%3A%3E2000", "UTF-8"
            )
        )
    }
}