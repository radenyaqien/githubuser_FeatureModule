package com.radenyaqien.core.domain

import com.radenyaqien.core.Resource
import com.radenyaqien.core.domain.model.DetailUser
import com.radenyaqien.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface Repository {


    suspend fun searchUser(query: String): Flow<Resource<List<GithubUser>>>

    suspend fun getDetailUser(username: String): Flow<Resource<DetailUser>>
}