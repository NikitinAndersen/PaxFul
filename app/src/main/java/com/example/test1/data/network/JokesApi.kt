package com.example.test1.data.network

import com.example.test1.data.model.BaseJokesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {

    @GET("jokes/")
    suspend fun getJokes(): BaseJokesResponse

    @GET("jokes/")
    suspend fun getJokes(@Query("firstName") firstName: String?, @Query("lastName") lastName: String?): BaseJokesResponse
}