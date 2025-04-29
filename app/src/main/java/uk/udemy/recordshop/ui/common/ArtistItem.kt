package uk.udemy.recordshop.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.udemy.recordshop.data.model.ArtistDTO

@Composable
fun ArtistItem(
    artistDTO: ArtistDTO,
    navigateToArtistAlbums: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .clickable { navigateToArtistAlbums(artistDTO.id) },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = artistDTO.artistName,
            fontSize = 20.sp
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .height(1.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ArtistItemPreview() {
    ArtistItem(
        artistDTO = ArtistDTO(
            id = 1,
            artistName = "Davido"
        ),
        navigateToArtistAlbums = {}
    )
}