package uk.udemy.recordshop.ui.screens.genre

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.udemy.recordshop.ui.common.AlbumsList
import uk.udemy.recordshop.ui.common.DefaultErrorScreen
import uk.udemy.recordshop.ui.common.DefaultNetworkErrorScreen
import uk.udemy.recordshop.ui.common.DefaultProgressIndicator

@Composable
fun GenreScreenContent(
    state: GenreViewModel.State,
    onAlbumItemClicked: (Long) -> Unit,
    onTryAgainButtonClicked: () -> Unit
) {
    when (state) {
        is GenreViewModel.State.Error -> {
            DefaultErrorScreen(
                responseCode = state.responseCode,
                errorMessage = state.error
            )
        }

        is GenreViewModel.State.Loaded -> {
            GenreScreenContentLoaded(
                state = state,
                onAlbumItemClicked = onAlbumItemClicked
            )
        }

        GenreViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }

        is GenreViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
                errorMessage = state.error,
                onTryAgainButtonClicked = onTryAgainButtonClicked
            )
        }
    }
}

@Composable
private fun GenreScreenContentLoaded(
    state: GenreViewModel.State.Loaded,
    onAlbumItemClicked: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            text = state.data.genre
        )
        AlbumsList(
            albums = state.data.albums,
            navigateToAlbumDetail = { albumId ->
                onAlbumItemClicked(albumId)
            }
        )
    }
}