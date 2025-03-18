@file:Suppress("KotlinConstantConditions")

package uk.udemy.recordshop.ui.viewAlbum

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ViewAlbumScreen(
    viewModel: ViewAlbumViewModel,
    onEditFabClicked: (Long) -> Unit,
    onAlbumDeleted: () -> Unit
) {

    val context = LocalContext.current

    // Events observer
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ViewAlbumViewModel.Event.DeleteAlbumFailed -> {
                    Toast.makeText(
                        context,
                        "Failed to delete Album\nResponse Code${event.responseCode}, ${event.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ViewAlbumViewModel.Event.DeleteAlbumSuccessful -> {
                    onAlbumDeleted()
                }
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    ViewAlbumScreenContent(
        state = state,
        onDeleteFabClicked = viewModel::showDeleteAlbumDialog,
        onEditFabClicked = onEditFabClicked,
        onDismiss = viewModel::dismissDeleteAlbumDialog,
        onDeleteAlbumConfirmed = { albumId ->
            viewModel.deleteAlbum(albumId)
            viewModel.dismissDeleteAlbumDialog()
        },
    )
}