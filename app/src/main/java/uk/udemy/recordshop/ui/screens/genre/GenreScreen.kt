package uk.udemy.recordshop.ui.screens.genre

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GenreScreen(
    viewModel: GenreViewModel,
    onAlbumItemClicked: (Long) -> Unit
){
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event) {
                is GenreViewModel.Event.AlbumItemClicked -> onAlbumItemClicked(event.albumId)
            }
        }
    }

    GenreScreenContent(
        state = state.value,
        onAlbumItemClicked = viewModel::onAlbumItemClicked,
        onTryAgainButtonClicked = { /*TODO*/}
    )
}