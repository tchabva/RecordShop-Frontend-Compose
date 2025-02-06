package uk.udemy.recordshop.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.udemy.recordshop.data.model.Album
import uk.udemy.recordshop.ui.common.AlbumItem

// The Home Screen that the app will open to
@Composable
fun HomeScreen(paddingValues: PaddingValues){

    val viewModel = hiltViewModel<HomeViewModel>()
    val viewState by viewModel.homeScreenState

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){

        when (viewState.isLoading){
            true-> {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            false -> {
                if (viewState.error == null){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
//                    items((viewState as HomeScreenState.Loaded).list){
//                            album ->
//                        AlbumItem(album)
//                    }
                        items(viewState.data){
                            album ->
                            AlbumItem(album)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun AlbumsList(
    albums: List<Album>,
    navigateToAlbumDetail: (Int) -> Unit
){
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        items(albums){

        }
    }
}