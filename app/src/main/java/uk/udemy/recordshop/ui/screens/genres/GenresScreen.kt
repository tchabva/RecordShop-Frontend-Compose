package uk.udemy.recordshop.ui.screens.genres

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GenresScreen(
    viewModel: GenresViewModel,
    onGenreItemClicked: (Long) -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val onRefresh: () -> Unit = {
        viewModel.getGenres()
    }

    // Events Observer
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is GenresViewModel.Event.GenreItemClicked -> onGenreItemClicked(event.genreId)
            }
        }
    }

    GenresScreensContent(
        state = state.value,
        onRefresh = onRefresh,
        onGenreItemClicked = viewModel::onGenreItemClicked,
        onTryAgainButtonClicked = viewModel::onTryAgainButtonClicked
    )
}