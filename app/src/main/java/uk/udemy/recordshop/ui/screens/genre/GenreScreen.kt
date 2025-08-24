package uk.udemy.recordshop.ui.screens.genre

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GenreScreen(
    viewModel: GenreViewModel,
    onAlbumItemClicked: (Long) -> Unit
){
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event) {
                is GenreViewModel.Event.AlbumItemClicked -> onAlbumItemClicked(event.albumId)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Genres Screen")
    }
}