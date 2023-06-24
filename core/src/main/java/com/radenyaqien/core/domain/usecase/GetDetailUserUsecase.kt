package com.radenyaqien.core.domain.usecase

import com.radenyaqien.core.Resource
import com.radenyaqien.core.domain.Repository
import com.radenyaqien.core.domain.model.DetailUser
import kotlinx.coroutines.flow.Flow

class GetDetailUserUsecase(private val repository: Repository) {

    suspend operator fun invoke(username: String): Flow<Resource<DetailUser>> {
        return repository.getDetailUser(username)
    }
}