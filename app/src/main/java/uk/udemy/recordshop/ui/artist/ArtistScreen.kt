package uk.udemy.recordshop.ui.artist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ArtistScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Artist Screen")
    }
}