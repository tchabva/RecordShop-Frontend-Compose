@file:Suppress("KotlinConstantConditions")

package uk.udemy.recordshop.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.R
import uk.udemy.recordshop.data.model.Album
import uk.udemy.recordshop.ui.common.AlbumsList
import uk.udemy.recordshop.ui.common.FloatingActionButtonTemplate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    pullToRefreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    onAddAlbumClick: () -> Unit,
    onAlbumClicked: (Long) -> Unit
) {
    when {
        state.isLoading -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        state.error != null -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullToRefresh(
                        isRefreshing = state.isLoading,
                        state = pullToRefreshState,
                        onRefresh = onRefresh
                    )
                    .verticalScroll(rememberScrollState()),

            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.error_occurred)
                )
            }
        }

        else -> {
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
                    onAlbumClicked(it)
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        state = HomeScreenState(
            isLoading = false,
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
        onAlbumClicked = {},
        pullToRefreshState = rememberPullToRefreshState(),
        onRefresh = {},
    ) 
}