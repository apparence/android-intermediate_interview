package com.onespan.android.interview.retrofit

import com.onespan.android.interview.model.Breeds
import retrofit2.Response

class ApiServiceImpl(
    private val apiService: ApiService
) {

    suspend fun getBreeds(limit: Int, page: Int) : Response<Breeds> {
        return apiService.getBreeds(limit, page)
    }
}