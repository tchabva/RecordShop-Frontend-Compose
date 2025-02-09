package uk.udemy.recordshop.ui.viewAlbum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.udemy.recordshop.ui.navigation.Screens

@Composable
fun ViewAlbumScreen(
    viewAlbum: Screens.ViewAlbum
){

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("View Album Screen\n The Album ID is ${viewAlbum.albumId}")
    }
}