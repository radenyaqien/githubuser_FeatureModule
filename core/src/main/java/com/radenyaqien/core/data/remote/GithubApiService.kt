package com.radenyaqien.core.data.remote

import com.radenyaqien.core.data.remote.model.DetailUserResponse
import com.radenyaqien.core.data.remote.model.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): SearchUserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

}