package com.kazantsev.coder.repo.api

import com.kazantsev.coder.repo.api.model.ResponseApi
import retrofit2.Response
import retrofit2.http.GET

interface DataSource {
    @GET("users")
    suspend fun getUsers(): Response<ResponseApi>
}