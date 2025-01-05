package uk.udemy.recordshop.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.model.Album

// The Home Screen that the app will open to
@Composable
fun HomeScreen(paddingValues: PaddingValues, albums: List<Album>){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

        }
    }
}


