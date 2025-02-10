package uk.udemy.recordshop.ui.viewAlbum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.R

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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("The Album ID is ${state.data!!.id}")
                Text("The Album Title is ${state.data.title}")
                Text("The Album Artist is ${state.data.artist}")
                Text("The Album Genre is ${state.data.genre}")
                Text("The Album Price is ${state.data.price}")
                Text("The Album Stock is ${state.data.stock}")
                Text("The Album Release Date is ${state.data.releaseDate}")
            }
        }
    }
}