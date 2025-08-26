package uk.udemy.recordshop.ui.screens.genres

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.ui.common.DefaultErrorScreen
import uk.udemy.recordshop.ui.common.DefaultNetworkErrorScreen
import uk.udemy.recordshop.ui.common.DefaultProgressIndicator
import uk.udemy.recordshop.ui.common.GenreItem

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
                onTryAgainButtonClicked = { /*TODO*/ }
            )
        }

        is GenresViewModel.State.Loaded -> {
            GenresScreenLoaded(
                state = state,
                onRefresh = onRefresh,
                onGenreItemClicked = onGenreItemClicked
            )
        }

        GenresViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }

        is GenresViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
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
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(state.data) { genreDTO ->
                GenreItem(
                    genreDTO = genreDTO,
                    navigateToGenreAlbums = onGenreItemClicked
                )
            }
        }
    }
}