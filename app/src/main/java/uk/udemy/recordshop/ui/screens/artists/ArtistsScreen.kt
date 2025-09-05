package uk.udemy.recordshop.ui.screens.artists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ArtistsScreen(
    viewModel: ArtistsViewModel,
    onArtistItemClicked: (Long) -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
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
        onRefresh = onRefresh,
        onArtistItemClick = viewModel::onArtistItemClicked,
        onTryAgainButtonClicked = viewModel::onTryAgainButtonClicked
    )
}