package uk.udemy.recordshop.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.data.model.Album

@Composable
fun AlbumsList(
    albums: List<Album>,
    navigateToAlbumDetail: (Long) -> Unit
){
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        items(albums){
            AlbumItem(it, navigateToAlbumDetail)
        }
    }
}