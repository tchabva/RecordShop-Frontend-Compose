@file:Suppress("KotlinConstantConditions")

package uk.udemy.recordshop.ui.viewAlbum

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import uk.udemy.recordshop.ui.navigation.Screens

@Composable
fun ViewAlbumScreen(
    viewModel: ViewAlbumViewModel,
    onDeleteFabClicked: (Long) -> Unit,
    onEditFabClicked: (Long) -> Unit
) {
//    viewModel.getAlbumById(viewAlbum.albumId)
    val viewState by viewModel.viewAlbumScreenState
    var showDialog by remember { mutableStateOf(false) }

    ViewAlbumScreenContent(
        state = viewState,
        onDeleteFabClicked = {
            showDialog = true
            Log.i("ViewAlbumScreen", "Delete FAB clicked: $showDialog")
        },
        onEditFabClicked = onEditFabClicked,
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onDeleteAlbumConfirmed = { albumId ->

            showDialog = false
            viewModel.deleteAlbum(albumId)
        }
    )
}