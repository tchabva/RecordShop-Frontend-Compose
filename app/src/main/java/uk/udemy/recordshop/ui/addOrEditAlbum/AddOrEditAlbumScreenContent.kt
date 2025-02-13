package uk.udemy.recordshop.ui.addOrEditAlbum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddOrEditAlbumScreenContent(
    state: AddOrEditAlbumViewModel.State,
){

    when(state){
        AddOrEditAlbumViewModel.State.AddAlbum -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Add Album View")
            }
        }
        is AddOrEditAlbumViewModel.State.EditAlbum -> {
            TODO()
        }
        is AddOrEditAlbumViewModel.State.Error -> {
            TODO()
        }
        AddOrEditAlbumViewModel.State.Loading -> {
            TODO()
        }
        is AddOrEditAlbumViewModel.State.NetworkError -> {
            TODO()
        }
    }

}