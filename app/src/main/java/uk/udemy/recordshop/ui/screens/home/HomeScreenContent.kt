package uk.udemy.recordshop.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.R
import uk.udemy.recordshop.data.model.Album
import uk.udemy.recordshop.ui.common.AlbumsList
import uk.udemy.recordshop.ui.common.DefaultErrorScreen
import uk.udemy.recordshop.ui.common.DefaultNetworkErrorScreen
import uk.udemy.recordshop.ui.common.DefaultProgressIndicator
import uk.udemy.recordshop.ui.common.FloatingActionButtonTemplate

@Composable
fun HomeScreenContent(
    state: HomeViewModel.State,
    onRefresh: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumItemClick: (Long) -> Unit,
    onTryAgainButtonClicked: () -> Unit
) {
    when (state) {
        is HomeViewModel.State.Error -> {
            DefaultErrorScreen(
                responseCode = state.responseCode ?: 0,
                errorMessage = state.errorMessage
            )
        }

        is HomeViewModel.State.Loaded -> {
            HomeScreenLoaded(
                state = state,
                onRefresh = onRefresh,
                onAddAlbumClick = onAddAlbumClick,
                onAlbumItemClick = onAlbumItemClick
            )
        }

        HomeViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }

        is HomeViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
                errorMessage = state.errorMessage,
                onTryAgainButtonClicked = onTryAgainButtonClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLoaded(
    state: HomeViewModel.State.Loaded,
    onRefresh: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumItemClick: (Long) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        modifier = Modifier

    ) {
        AlbumsList(
            state.data
        ) {
            onAlbumItemClick(it)
        }

        FloatingActionButtonTemplate(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            Icons.Default.Add,
            R.string.fab_add_new_album_txt
        ) {
            Log.i("HomeScreen", "Add New Button Clicked")
            onAddAlbumClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        state = HomeViewModel.State.Loaded(
            data = listOf(
                element = Album(
                    id = 1,
                    title = "Test",
                    artist = "Test",
                    genre = "Rap",
                    releaseDate = "2025-01-11",
                    stock = 4,
                    price = 10.99,
                    artworkUrl = null,
                    dateCreated = null,
                    dateModified = null
                )
            ),
        ),
        onAddAlbumClick = {},
        onAlbumItemClick = {},
        onRefresh = {},
        onTryAgainButtonClicked = {},
    )
}