package uk.udemy.recordshop.ui.addOrEditAlbum

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddOrEditAlbumScreen(
    viewModel: AddOrEditAlbumViewModel
){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect{ event ->
            when(event){
                AddOrEditAlbumViewModel.Event.AlbumAdded -> {
                    TODO()
                }
                AddOrEditAlbumViewModel.Event.AlbumNotAdded -> {
                    TODO()
                }
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()
    AddOrEditAlbumScreenContent(
        state = state.value,

    )
}