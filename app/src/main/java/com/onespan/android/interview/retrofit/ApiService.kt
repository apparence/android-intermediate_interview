package com.onespan.android.interview.retrofit

import com.onespan.android.interview.model.Breeds
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("breeds")
    suspend fun getBreeds(@Query("limit") limit: Int, @Query("page") page: Int): Response<Breeds>
}