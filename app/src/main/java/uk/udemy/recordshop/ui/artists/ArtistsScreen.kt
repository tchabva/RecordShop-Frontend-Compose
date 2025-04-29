package uk.udemy.recordshop.ui.artists

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    viewModel: ArtistsViewModel,
    onArtistItemClicked: (Long) -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()
    val onRefresh: () -> Unit = {
        viewModel.getArtists()
    }

    // Events Observer
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ArtistsViewModel.Event.ArtistItemClicked -> onArtistItemClicked(event.artistId)
            }
        }
    }

    ArtistsScreenContent(
        state = state.value,
        pullToRefreshState = pullToRefreshState,
        onRefresh = onRefresh,
        onArtistItemClick = viewModel::onArtistItemClicked,
        onTryAgainButtonClicked = viewModel::onTryAgainButtonClicked
    )
}