package uk.udemy.recordshop.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

// The Home Screen that the app will open to
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddAlbumClick: () -> Unit
) {

    val viewState by viewModel.homeScreenState

    HomeScreenContent(
        state = viewState,
        onAddAlbumClick = onAddAlbumClick
    ) { }
}

