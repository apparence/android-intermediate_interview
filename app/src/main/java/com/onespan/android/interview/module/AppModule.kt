package com.onespan.android.interview.module

import com.onespan.android.interview.retrofit.ApiService
import com.onespan.android.interview.retrofit.ApiServiceImpl
import com.onespan.android.interview.retrofit.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit { return RetrofitBuilder.retrofit}

    @Provides
    @Singleton
    fun provideApiService(): ApiServiceImpl { return ApiServiceImpl(RetrofitBuilder.apiService)
    }

    @Provides
    @Singleton
    fun provideCoroutine() : CoroutineScope { return CoroutineScope(Dispatchers.IO) }

}