package uk.udemy.recordshop.ui.addOrEditAlbum

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddOrEditAlbumScreen(
    viewModel: AddOrEditAlbumViewModel,
    navigateToHomeGraph: () -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AddOrEditAlbumViewModel.Event.AlbumAdded -> {
                    navigateToHomeGraph()
                }

                is AddOrEditAlbumViewModel.Event.MandatoryTextFieldEmpty -> {
                    Toast.makeText(
                        context,
                        event.attribute,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is AddOrEditAlbumViewModel.Event.AlbumNotAdded -> {
                    Toast.makeText(
                        context,
                        "Code: ${event.responseCode} \n${event.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                AddOrEditAlbumViewModel.Event.AlbumUnchanged -> {
                    Toast.makeText(
                        context,
                        "No changes made to Album!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()
    AddOrEditAlbumScreenContent(
        state = state.value,
        addAlbum = viewModel::addAlbum,
        updateAlbum = viewModel::updateAlbum
    )
}