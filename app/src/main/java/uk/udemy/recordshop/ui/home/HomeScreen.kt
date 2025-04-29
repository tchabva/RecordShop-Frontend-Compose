package uk.udemy.recordshop.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

// The Home Screen that the app will open to
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddAlbumClick: () -> Unit,
    onAlbumItemClick: (Long) -> Unit
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val onRefresh: () -> Unit = {
        viewModel.getAlbums()
    }

    // Events Observer
    LaunchedEffect(Unit) {
        viewModel.events.collect{ event ->
            when (event){
                HomeViewModel.Event.AddAlbumClicked -> onAddAlbumClick()
                is HomeViewModel.Event.AlbumItemClicked -> {
                    onAlbumItemClick(event.albumId)
                }
            }
        }
    }

    HomeScreenContent(
        state = state.value ,
        onAddAlbumClick = viewModel::addAlbumFabClicked,
        onRefresh = onRefresh,
        onAlbumItemClick = viewModel::onAlbumItemClicked,
        onTryAgainButtonClicked = viewModel::onTryAgainButtonClicked
    )
}