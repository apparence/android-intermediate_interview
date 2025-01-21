package com.onespan.android.interview.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.onespan.android.interview.main.paging.BreedPagingSource
import com.onespan.android.interview.model.dto.Breed
import com.onespan.android.interview.retrofit.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val apiService: ApiServiceImpl,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val limit = 20

    private val flow = Pager(PagingConfig(pageSize = limit)) {
        BreedPagingSource(apiService, limit)
    }.flow

    private val _uiState = MutableStateFlow<PagingData<Breed>>(PagingData.empty())
    val uiState : StateFlow<PagingData<Breed>> = _uiState.asStateFlow()

    fun getBreed() {
        coroutineScope.launch {
            try {
                flow.collectLatest { pagingData ->
                    _uiState.value = pagingData
                }
            } catch (e : Exception) {
                Log.e("ViewModel", e.toString())
            }
        }
    }
}