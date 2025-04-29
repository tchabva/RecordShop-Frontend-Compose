@file:OptIn(ExperimentalMaterial3Api::class)

package uk.udemy.recordshop.ui.artists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.ui.common.ArtistItem
import uk.udemy.recordshop.ui.common.DefaultErrorScreen
import uk.udemy.recordshop.ui.common.DefaultNetworkErrorScreen
import uk.udemy.recordshop.ui.common.DefaultProgressIndicator

@Composable
fun ArtistsScreenContent(
    state: ArtistsViewModel.State,
    pullToRefreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    onArtistItemClick: (Long) -> Unit,
    onTryAgainButtonClicked: () -> Unit
) {
    when (state) {
        is ArtistsViewModel.State.Error -> {
            DefaultErrorScreen(
                responseCode = state.responseCode ?: 0,
                errorMessage = state.errorMessage
            )
        }

        is ArtistsViewModel.State.Loaded -> {
            ArtistsScreenLoaded(
                state = state,
                pullToRefreshState = pullToRefreshState,
                onRefresh = onRefresh,
                onArtistItemClick = onArtistItemClick
            )
        }

        ArtistsViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }

        is ArtistsViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
                errorMessage = state.errorMessage,
                onTryAgainButtonClicked = onTryAgainButtonClicked
            )
        }
    }
}

@Composable
fun ArtistsScreenLoaded(
    state: ArtistsViewModel.State.Loaded,
    pullToRefreshState: PullToRefreshState,
    onRefresh: () -> Unit,
    onArtistItemClick: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullToRefresh(
                isRefreshing = state.isLoading,
                state = pullToRefreshState,
                onRefresh = onRefresh
            ),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(state.data) { artistDTO ->
                ArtistItem(artistDTO = artistDTO, navigateToArtistAlbums = onArtistItemClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistScreenContentLoadedPreview() {
    ArtistsScreenContent(
        state = ArtistsViewModel.State.Loaded(),
        pullToRefreshState = rememberPullToRefreshState(),
        onRefresh = {},
        onArtistItemClick = {},
        onTryAgainButtonClicked = {}
    )
}