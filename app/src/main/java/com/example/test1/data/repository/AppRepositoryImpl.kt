package com.example.test1.data.repository

import com.example.test1.data.network.JokesApi
import com.example.test1.data.model.BaseJokesResponse

class AppRepositoryImpl(private val api: JokesApi) : AppRepository {

    override suspend fun getJokes(): BaseJokesResponse {
        return api.getJokes()
    }

    override suspend fun getJokes(firstName: String?, lastName: String?): BaseJokesResponse {
        return api.getJokes(firstName, lastName)
    }
}