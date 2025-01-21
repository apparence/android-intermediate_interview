package com.onespan.android.interview

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onespan.android.interview.model.dto.ErrorResponse


/*
val gson = Gson()
val type = object : TypeToken<ErrorResponse>() {}.type
var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
 */

fun String.fromGson() : ErrorResponse {
    val gson = Gson()
    val type = object : TypeToken<ErrorResponse>() {}.type
    return gson.fromJson(this, type)
}