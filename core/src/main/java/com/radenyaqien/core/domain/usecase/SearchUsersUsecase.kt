package com.radenyaqien.core.domain.usecase

import com.radenyaqien.core.Resource
import com.radenyaqien.core.domain.Repository
import com.radenyaqien.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUsersUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(query: String): Flow<Resource<List<GithubUser>>> {
        return repository.searchUser(query)
    }
}