package uk.udemy.recordshop.ui.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

// The Home Screen that the app will open to
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAddAlbumClick: () -> Unit,
    onAlbumClicked: (Long) -> Unit
) {

    val viewState by viewModel.homeScreenState
    val pullToRefreshState = rememberPullToRefreshState()
    val onRefresh: () -> Unit = {
        viewModel.getAlbums()
    }

    HomeScreenContent(
        state = viewState,
        onAddAlbumClick = onAddAlbumClick,
        pullToRefreshState = pullToRefreshState,
        onRefresh = onRefresh,
        onAlbumClicked = onAlbumClicked
    )
}

