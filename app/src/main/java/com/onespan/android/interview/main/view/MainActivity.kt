package com.onespan.android.interview.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.onespan.android.interview.AndroidIntermediateInterviewTheme
import com.onespan.android.interview.R
import com.onespan.android.interview.main.viewmodel.MainActivityViewModel
import com.onespan.android.interview.model.Breed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flowOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainActivityViewModel.getBreed()
        setContent {
            AndroidIntermediateInterviewTheme {
                ScreenContent(mainActivityViewModel)
            }
        }
    }
}

@Composable
fun ScreenContent(
    mainActivityViewModel: MainActivityViewModel
) {
    val breeds by rememberUpdatedState(newValue = mainActivityViewModel.uiState.collectAsLazyPagingItems())
    mainActivityViewModel.getBreed()
    CatBreeds(
        modifier = Modifier.fillMaxSize(),
        breeds
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(title = { Text(stringResource(R.string.app_name)) })
}

@Composable
fun CatBreeds(
    modifier: Modifier = Modifier,
    breeds: LazyPagingItems<Breed>
) {
    Scaffold(
        topBar = { TopBar() },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(breeds.itemCount) { index ->
                val breed = breeds[index]
                breed?.let {
                    Text(text = breed.breed ?: "")
                    Text(text = breed.country ?: "")
                    Text(text = breed.origin ?: "")
                    Text(text = breed.coat ?: "")
                    Text(text = breed.pattern ?: "")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenContentPreview() {
    AndroidIntermediateInterviewTheme {
        CatBreeds(modifier = Modifier.fillMaxSize(),
            flowOf(PagingData.from(mutableListOf(
                Breed("breed", "country", "origin", "coat", "patter"),
                Breed("breed", "country", "origin", "coat", "patter"),
                Breed("breed", "country", "origin", "coat", "patter"),
                Breed("breed", "country", "origin", "coat", "patter"),
                Breed("breed", "country", "origin", "coat", "patter"),
                ))).collectAsLazyPagingItems()
        )
    }
}