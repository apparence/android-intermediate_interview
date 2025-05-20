package com.onespan.android.interview.retrofit

import com.onespan.android.interview.extension.fromGson
import com.onespan.android.interview.model.Result
import com.onespan.android.interview.model.dto.Breeds
import com.onespan.android.interview.model.dto.ErrorResponse
import javax.inject.Inject

class ApiServiceRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getBreeds(limit: Int, page: Int) : Result<Breeds> {
        return try {
            val result = apiService.getBreeds(limit, page)
            result.body()?.let {
                if (result.isSuccessful) {
                    Result.Success(it)
                } else {
                    Result.GenericError(ErrorResponse(result.code(), result.message()))
                }
            } ?: run {
                Result.GenericError(result.errorBody()?.string()?.fromGson()!!)
            }

        } catch (e : Exception) {
            Result.GenericError(ErrorResponse(400, e.message ?: ""))
        }
    }
}