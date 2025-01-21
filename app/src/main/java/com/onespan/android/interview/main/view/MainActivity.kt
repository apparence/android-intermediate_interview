package com.onespan.android.interview.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.onespan.android.interview.AndroidIntermediateInterviewTheme
import com.onespan.android.interview.R
import com.onespan.android.interview.main.viewmodel.MainActivityViewModel
import com.onespan.android.interview.model.dto.Breed
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

    Scaffold(
        topBar = { TopBar() },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val loadState = breeds.loadState
        when {
            ((loadState.refresh is LoadState.Loading && breeds.itemCount == 0)) ||
                    loadState.append is LoadState.Loading -> {
                DisplayLoader()
            }
            (loadState.hasError) -> {
                DisplayErrorMessage(loadState.refresh as LoadState.Error)
            }

            else -> {
                DisplayLazyList(
                    modifier = Modifier.fillMaxSize(),
                    innerPadding,
                    breeds
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(title = { Text(stringResource(R.string.app_name)) })
}

@Composable
fun DisplayErrorMessage(error: LoadState.Error) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(error.error.message ?: "", color = colorResource(R.color.red_error))
    }
}

@Composable
fun DisplayLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = dimensionResource(R.dimen.dp_8),
            color = colorResource(R.color.teal_700)
        )
    }
}

@Composable
fun DisplayLazyList(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    breeds: LazyPagingItems<Breed>
) {
    LazyColumn(
        modifier = modifier.padding(innerPadding),
        contentPadding = PaddingValues(dimensionResource(R.dimen.dp_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dp_16))
    ) {
        items(breeds.itemCount) { index ->
            breeds[index]?.let { breed ->
                DisplayBreed(breed)
            }
        }
    }

}

@Composable
fun DisplayBreed(breed: Breed) {
    Text(text = breed.breed ?: "")
    Text(text = breed.country ?: "")
    Text(text = breed.origin ?: "")
    Text(text = breed.coat ?: "")
    Text(text = breed.pattern ?: "")
    HorizontalDivider(modifier = Modifier.fillMaxSize(), color = colorResource(R.color.black))
}

@Preview(showBackground = true)
@Composable
fun ScreenContentPreview() {
    AndroidIntermediateInterviewTheme {
        DisplayLazyList(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(dimensionResource(R.dimen.dp_16)),
            flowOf(
                PagingData.from(
                    mutableListOf(
                        Breed("breed", "country", "origin", "coat", "patter"),
                        Breed("breed", "country", "origin", "coat", "patter"),
                        Breed("breed", "country", "origin", "coat", "patter"),
                        Breed("breed", "country", "origin", "coat", "patter"),
                        Breed("breed", "country", "origin", "coat", "patter"),
                    )
                )
            ).collectAsLazyPagingItems()
        )
    }
}