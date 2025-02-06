package uk.udemy.recordshop.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.udemy.recordshop.R
import uk.udemy.recordshop.ui.common.AlbumsList

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

        when{
            viewState.isLoading ->{

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
            viewState.error != null ->{

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)){
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(R.string.error_occurred)
                    )
                }
            }
            else ->{
                AlbumsList(
                    viewState.data
                ) {
                    viewModel.navigateToAlbumDetail(it)
                }
            }
        }
    }
}