package uk.udemy.recordshop.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.udemy.recordshop.R

@Composable
fun DefaultNetworkErrorScreen(onTryAgainButtonClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            text = stringResource(R.string.network_error_occurred)
        )

        Button(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(.75f),
            onClick = onTryAgainButtonClicked
        ) {
            Text(text = "Try Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultNetworkErrorScreenPreview() {
    DefaultNetworkErrorScreen(
        onTryAgainButtonClicked = {}
    )
}