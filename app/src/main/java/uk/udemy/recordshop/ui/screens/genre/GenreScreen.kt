package uk.udemy.recordshop.ui.screens.genre

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GenreScreen(
    viewModel: GenreViewModel,
    onAlbumItemClicked: (Long) -> Unit,
    onTryAgainButtonClicked: () -> Unit
){
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event) {
                is GenreViewModel.Event.AlbumItemClicked -> onAlbumItemClicked(event.albumId)
                GenreViewModel.Event.TryAgainButtonClicked -> onTryAgainButtonClicked()
            }
        }
    }

    GenreScreenContent(
        state = state.value,
        onAlbumItemClicked = viewModel::onAlbumItemClicked,
        onTryAgainButtonClicked = viewModel::onTryAgainButtonClicked
    )
}