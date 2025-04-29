package uk.udemy.recordshop.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uk.udemy.recordshop.R

// TODO add to other screens
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultNetworkErrorScreen(errorMessage: String?, isLoading: Boolean, onRefresh: () -> Unit) {
    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        isRefreshing = isLoading,
        onRefresh = onRefresh
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(
                R.string.network_error_occurred,
                errorMessage ?: stringResource(R.string.unknown_error)
            )
        )
    }
}