@file:Suppress("KotlinConstantConditions")

package uk.udemy.recordshop.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeViewModel.State,
    pullToRefreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumItemClicked: (Long) -> Unit
) {
    when(state){
        is HomeViewModel.State.Error -> {
            DefaultErrorScreen(
                responseCode = state.responseCode ?: 0,
                errorMessage = state.errorMessage
            )
        }
        is HomeViewModel.State.Loaded -> {
                        Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullToRefresh(
                        isRefreshing = state.isLoading,
                        state = pullToRefreshState,
                        onRefresh = onRefresh
                    ),

            ) {
                AlbumsList(
                    state.data
                ) {
                    onAlbumItemClicked(it)
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
        HomeViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }
        is HomeViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
                errorMessage = state.errorMessage
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
        onAlbumItemClicked = {},
        pullToRefreshState = rememberPullToRefreshState(),
        onRefresh = {},
    )
}