package uk.udemy.recordshop.ui.viewAlbum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import uk.udemy.recordshop.ui.navigation.Screens

@Composable
fun ViewAlbumScreen(
    viewAlbum: Screens.ViewAlbum,
    viewModel: ViewAlbumViewModel,
    onDeleteFabClicked: (Long) -> Unit,
    onEditFabClicked: (Long) -> Unit
){
    viewModel.getAlbumById(viewAlbum.albumId)
    val viewState by viewModel.viewAlbumScreenState

    ViewAlbumScreenContent(
        viewState,
        onDeleteFabClicked = onDeleteFabClicked,
        onEditFabClicked = onEditFabClicked
    )
}