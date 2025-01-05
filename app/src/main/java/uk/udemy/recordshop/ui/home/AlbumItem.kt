package uk.udemy.recordshop.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import uk.udemy.recordshop.R
import uk.udemy.recordshop.model.Album

@Composable
fun AlbumItem(album: Album){

    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp)
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(color =  Color.LightGray)
                .clickable {  },
            verticalAlignment = Alignment.CenterVertically

        ){
            Image(
                painter = rememberAsyncImagePainter(
                    model = album.artworkUrl,
                    placeholder = painterResource(R.drawable.holder_album_artwork),
                    error = painterResource(R.drawable.holder_album_artwork)
                ),
                contentDescription = "Artwork",
                modifier = Modifier.padding(16.dp).size(120.dp)
            )

            Column(
                Modifier.fillMaxWidth()
            ) {
                Text(album.title)
                Text(album.artist)
                Text(album.genre)
                Text("Release Date: ${album.releaseDate}")
                Text("Price: £${album.price}")
                Text("Stock: ${album.stock}")
            }
        }

    }


}

@Preview
@Composable
fun AlbumItemPreview(){
    AlbumItem(Album(
        id = 1,
        title = "Timeless",
        artist = "Davido",
        genre = "Amapiano",
        releaseDate = "2023-11-11",
        stock = 3,
        price = 2.99,
        dateCreated = null,
        dateModified = null,
        artworkUrl = null,
    ))
}

