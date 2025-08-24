package uk.udemy.recordshop.ui.screens.genres

import androidx.compose.runtime.Composable
import uk.udemy.recordshop.ui.common.DefaultErrorScreen
import uk.udemy.recordshop.ui.common.DefaultNetworkErrorScreen
import uk.udemy.recordshop.ui.common.DefaultProgressIndicator

@Composable
fun GenresScreensContent(
    state: GenresViewModel.State,
    onRefresh: () -> Unit,
    onGenreItemClicked: (Long) -> Unit,
    onTryAgainButtonClicked: () -> Unit
) {
    when (state) {

        is GenresViewModel.State.Error -> {
            DefaultErrorScreen(
                responseCode = state.responseCode ?: 0,
                errorMessage = state.errorMessage
            )
        }
        is GenresViewModel.State.Loaded -> {
            GenresScreenLoaded(

            )
        }
        GenresViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }
        is GenresViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
                errorMessage = state.errorMessage,
                onTryAgainButtonClicked = onTryAgainButtonClicked
            )
        }
    }
}

@Composable
fun GenresScreenLoaded(
    state: GenresViewModel.State.Loaded,
    onRefresh: () -> Unit,
    onGenreItemClicked: (Long) -> Unit
) {
    
}
