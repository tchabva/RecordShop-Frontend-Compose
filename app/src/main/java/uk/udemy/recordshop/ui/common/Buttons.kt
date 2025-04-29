package uk.udemy.recordshop.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun FloatingActionButtonTemplate(
    modifier: Modifier,
    icon: ImageVector,
    stringRes: Int,
    onClick: () -> Unit,

    ){
    androidx.compose.material3.FloatingActionButton(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        onClick = {
            onClick()
        },
    ) {
        Icon(icon, stringResource(stringRes))
    }
}