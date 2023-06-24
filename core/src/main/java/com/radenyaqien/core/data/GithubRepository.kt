package com.radenyaqien.core.data

import android.util.Log
import com.radenyaqien.core.Resource
import com.radenyaqien.core.data.remote.GithubApiService
import com.radenyaqien.core.data.remote.model.asDetailUser
import com.radenyaqien.core.data.remote.model.asGithubUser
import com.radenyaqien.core.domain.Repository
import com.radenyaqien.core.domain.model.DetailUser
import com.radenyaqien.core.domain.model.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class GithubRepository(private val apiService: GithubApiService) : Repository {
    override suspend fun searchUser(query: String): Flow<Resource<List<GithubUser>>> {
        return safeApiCall {
            apiService.searchUser(query).items.map {
                it.asGithubUser()
            }
        }
    }

    override suspend fun getDetailUser(username: String): Flow<Resource<DetailUser>> {
        return safeApiCall {
            apiService.getDetailUser(username).asDetailUser()
        }
    }


    private fun <Api> safeApiCall(
        apiCall: suspend () -> Api
    ) = flow<Resource<Api>> {
        emit(Resource.Loading())
        runCatching {
            apiCall.invoke()
        }.onSuccess {
            emit(Resource.Success(it))
        }.onFailure {
            Log.e("safeApiCall2: ", it.message, it)
            when (it) {
                is HttpException -> {
                    val res = it.response()?.errorBody()?.string()?.let { it1 -> JSONObject(it1) }
                    val error = res?.getString("message")
                    emit(
                        Resource.Error(
                            error ?: "Terjadi Kesalahan"
                        )
                    )
                }

                else -> {
                    emit(Resource.Error("Terjadi kesalahan Periksa Koneksi anda "))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}