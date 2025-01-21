package com.onespan.android.interview.model

import com.onespan.android.interview.model.dto.ErrorResponse


sealed class Result<out T> (val body: T? = null, val errorResponse: ErrorResponse? = null){
    data class Success<out T>(val value: T): Result<T>(
        body = value,
        errorResponse = null
    )
    data class GenericError(val errorMessage : ErrorResponse): Result<Nothing>(
        body = null,
        errorResponse =  errorMessage
    )
}