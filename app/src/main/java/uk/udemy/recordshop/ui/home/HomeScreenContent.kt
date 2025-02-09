package uk.udemy.recordshop.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.R
import uk.udemy.recordshop.ui.common.AlbumsList
import uk.udemy.recordshop.ui.common.FloatingActionButtonTemplate

@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    onAddAlbumClick: () -> Unit,
    onAlbumClicked: (Long) -> Unit
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AlbumsList(
                    state.data
                ) {
                    onAlbumClicked(it)
                }

                FloatingActionButtonTemplate(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    Icons.Default.Add,
                    R.string.fab_add_new_album_txt
                ) {
                    Log.i("HomeScreen", "Add New Button Clicked")
                    onAddAlbumClick()
                }
            }
        }
    }
}
