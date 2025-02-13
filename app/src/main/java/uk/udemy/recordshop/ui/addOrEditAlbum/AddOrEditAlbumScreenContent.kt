package uk.udemy.recordshop.ui.addOrEditAlbum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddOrEditAlbumScreenContent(
    state: AddOrEditAlbumViewModel.State,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Add Or Edit Album Screen Screen")
    }
}