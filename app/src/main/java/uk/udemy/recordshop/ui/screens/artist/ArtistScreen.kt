package uk.udemy.recordshop.ui.screens.artist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ArtistScreen(
    viewModel: ArtistViewModel,
    onAlbumItemClick: (Long) -> Unit,
    onTryAgainButtonClicked: () -> Unit,
    artistScreenTitle: (String) -> Unit
){

    val state = viewModel.state.collectAsStateWithLifecycle()

    // Events Observer
    LaunchedEffect(Unit) {
        viewModel.events.collect{ event ->
            when (event) {
                is ArtistViewModel.Event.AlbumItemClicked -> onAlbumItemClick(event.albumId)
                ArtistViewModel.Event.TryAgainButtonClicked -> onTryAgainButtonClicked()
            }
        }
    }

    ArtistScreenContent(
        state = state.value,
        onAlbumItemClicked = viewModel::onAlbumItemClicked,
        onTryAgainButtonClicked = viewModel::onTryAgainButtonClicked,
        artistTitle = artistScreenTitle,
    )
}