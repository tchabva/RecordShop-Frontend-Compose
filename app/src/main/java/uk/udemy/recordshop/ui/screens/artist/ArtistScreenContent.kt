package uk.udemy.recordshop.ui.screens.artist

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
fun ArtistScreenContent(
    state: ArtistViewModel.State,
    onAlbumItemClicked: (Long) -> Unit,
    onTryAgainButtonClicked: () -> Unit
) {

    when (state) {
        is ArtistViewModel.State.Error -> {
            DefaultErrorScreen(
                onTryAgainButtonClicked = onTryAgainButtonClicked
            )
        }

        is ArtistViewModel.State.Loaded -> {
            ArtistScreenLoaded(
                state = state,
                onAlbumItemClicked = onAlbumItemClicked
            )
        }

        ArtistViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }

        is ArtistViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
                onTryAgainButtonClicked = onTryAgainButtonClicked
            )
        }
    }
}

@Composable
fun ArtistScreenLoaded(
    state: ArtistViewModel.State.Loaded,
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
            text = state.data.artistName
        )
        AlbumsList(
            albums = state.data.albums,
            navigateToAlbumDetail = { albumId ->
                onAlbumItemClicked(albumId)
            }
        )
    }
}