package com.onespan.android.interview.main.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.onespan.android.interview.model.Breed
import com.onespan.android.interview.retrofit.ApiServiceImpl

class BreedPagingSource(
    private val apiServiceImpl: ApiServiceImpl,
    private val limit: Int,
) : PagingSource<Int, Breed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiServiceImpl.getBreeds(limit, currentPage)
            val breeds = response.body()?.breeds

            LoadResult.Page(
                data = breeds ?: mutableListOf(),
                prevKey = if (response.body()?.currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage < (response.body()?.lastPage ?: 0)) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Breed>): Int? {
        return null
    }

}