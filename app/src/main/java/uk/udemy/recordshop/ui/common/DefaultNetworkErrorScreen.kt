package uk.udemy.recordshop.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import uk.udemy.recordshop.R

@Composable
fun DefaultNetworkErrorScreen(errorMessage: String?) {
    Box(
        modifier = Modifier.fillMaxSize()
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