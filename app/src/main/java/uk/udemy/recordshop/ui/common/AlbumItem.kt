package uk.udemy.recordshop.ui.common

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.udemy.recordshop.R
import uk.udemy.recordshop.model.Album

@OptIn(ExperimentalGlideComposeApi::class)
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

            GlideImage(
                model = album.artworkUrl,
                contentDescription = "Album Artwork",
                loading = placeholder(R.drawable.holder_album_artwork),
                failure = placeholder(R.drawable.holder_album_artwork),
                modifier = Modifier.padding(8.dp).size(120.dp)
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
    AlbumItem(
        Album(
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
        )
    )
}

