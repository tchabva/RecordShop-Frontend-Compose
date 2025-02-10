package uk.udemy.recordshop.ui.viewAlbum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import uk.udemy.recordshop.ui.common.FloatingActionButtonTemplate

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ViewAlbumScreenContent(
    state: ViewAlbumScreenState
) {
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.error_occurred)
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Album Artwork if not null
                GlideImage(
                    modifier = Modifier.size(400.dp),
                    model = state.data?.artworkUrl,
                    contentDescription = state.data?.title?.let {
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
                        text = state.data!!.title,
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
                    ) { }

                    // Edit Album FAB
                    FloatingActionButtonTemplate(
                        modifier = Modifier,
                        icon = Icons.Default.Edit,
                        stringRes = R.string.delete_album_fab
                    ) { }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewAlbumScreenContentPreview() {
    ViewAlbumScreenContent(
        state = ViewAlbumScreenState(
            isLoading = false,
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
        )
    )
}