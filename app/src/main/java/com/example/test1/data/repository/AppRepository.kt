package com.example.test1.data.repository

import com.example.test1.data.model.BaseJokesResponse

interface AppRepository {

    suspend fun getJokes(): BaseJokesResponse

    suspend fun getJokes(firstName: String?, lastName: String?): BaseJokesResponse

}