package uk.udemy.recordshop.ui.viewAlbum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.udemy.recordshop.R
import uk.udemy.recordshop.data.model.Album
import uk.udemy.recordshop.ui.common.DefaultErrorScreen
import uk.udemy.recordshop.ui.common.DefaultNetworkErrorScreen
import uk.udemy.recordshop.ui.common.DefaultProgressIndicator
import uk.udemy.recordshop.ui.common.DeleteAlbumDialog
import uk.udemy.recordshop.ui.common.FloatingActionButtonTemplate

@Composable
fun ViewAlbumScreenContent(
    state: ViewAlbumViewModel.State,
    onDeleteFabClicked: () -> Unit,
    onEditFabClicked: (Long) -> Unit,
    onDismiss: () -> Unit,
    onDeleteAlbumConfirmed: (Long) -> Unit
) {
    when (state) {
        is ViewAlbumViewModel.State.Loading -> {
            DefaultProgressIndicator()
        }

        is ViewAlbumViewModel.State.Error -> {
            DefaultErrorScreen(
                responseCode = state.responseCode,
                errorMessage = state.error
            )
        }

        is ViewAlbumViewModel.State.NetworkError -> {
            DefaultNetworkErrorScreen(
                errorMessage = state.error,
                onTryAgainButtonClicked = { /*TODO*/ }
            )
        }

        is ViewAlbumViewModel.State.Loaded -> {
            ViewAlbumScreenLoaded(
                state = state,
                onDeleteFabClicked = onDeleteFabClicked,
                onEditFabClicked = onEditFabClicked,
                onDismiss = onDismiss,
                onDeleteAlbumConfirmed = onDeleteAlbumConfirmed
            )
        }
    }
}

// Composable for the View Album Screen Loaded State
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ViewAlbumScreenLoaded(
    state: ViewAlbumViewModel.State.Loaded,
    onDeleteFabClicked: () -> Unit,
    onEditFabClicked: (Long) -> Unit,
    onDismiss: () -> Unit,
    onDeleteAlbumConfirmed: (Long) -> Unit

){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Album Artwork if not null
        GlideImage(
            modifier = Modifier.size(400.dp),
            model = state.data.artworkUrl,
            contentDescription = state.data.title.let {
                stringResource(
                    R.string.album_artwork,
                    it
                )
            },
            loading = placeholder(R.drawable.holder_album_artwork),
            failure = placeholder(R.drawable.holder_album_artwork),
        )

        // Text Column
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Album Title
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.data.title,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )

            // Album Artist
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.data.artist,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Album Genre
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.data.genre,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            // Album Release Date
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.release_date, state.data.releaseDate),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )

            // Album Price
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.price_text, state.data.price),
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )

            // Album Stock
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.stock_text, state.data.stock),
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        }

        // FAB Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                Alignment.Bottom
            ),
            horizontalAlignment = Alignment.End
        ) {
            // Delete Album FAB
            FloatingActionButtonTemplate(
                modifier = Modifier,
                icon = Icons.Default.Delete,
                stringRes = R.string.delete_album_fab
            ) {
                onDeleteFabClicked()
            }

            // Edit Album FAB
            FloatingActionButtonTemplate(
                modifier = Modifier,
                icon = Icons.Default.Edit,
                stringRes = R.string.delete_album_fab
            ) {
                onEditFabClicked(state.data.id!!)
            }
        }
    }

    // Delete Album Dialog
    if (state.showDeleteAlbumDialog) {
        DeleteAlbumDialog(
            onDismiss = { onDismiss() },
            onDeleteAlbumConfirmed = { onDeleteAlbumConfirmed(state.data.id!!) }
        )
    }

    if (state.isLoading) {
        DefaultProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun ViewAlbumScreenContentPreview() {
    ViewAlbumScreenContent(
        state = ViewAlbumViewModel.State.Loaded(
            data = Album(
                id = 1,
                title = "Test Album",
                artist = "Test Artist",
                genre = "Genre",
                releaseDate = "2025-02-09",
                stock = 1,
                price = 10.99,
                artworkUrl = null,
                dateCreated = null,
                dateModified = null,
            ),
        ),
        onDeleteFabClicked = {},
        onEditFabClicked = {},
        onDismiss = {},
        onDeleteAlbumConfirmed = {},
    )
}