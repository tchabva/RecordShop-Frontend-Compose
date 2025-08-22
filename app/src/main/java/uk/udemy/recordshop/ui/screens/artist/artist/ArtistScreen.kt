package uk.udemy.recordshop.ui.screens.artist.artist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ArtistScreen(
    viewModel: ArtistViewModel,
    onAlbumItemClick: (Long) -> Unit
){

    val state = viewModel.state.collectAsStateWithLifecycle()

    // Events Observer
    LaunchedEffect(Unit) {
        viewModel.events.collect{ event ->
            when (event) {
                is ArtistViewModel.Event.AlbumItemClicked -> onAlbumItemClick(event.albumId)
            }
        }
    }

    ArtistScreenContent(
        state = state.value,
        onAlbumItemClicked = viewModel::onAlbumItemClicked
    )
}