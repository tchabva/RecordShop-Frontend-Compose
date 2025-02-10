package uk.udemy.recordshop.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uk.udemy.recordshop.R

// The Dialog Alert for deleting albums
@Composable
fun DeleteAlbumDialog(
    onDismiss: () -> Unit,
    onDeleteAlbumConfirmed: (Long) -> Unit
) {

    AlertDialog(
        icon = { Icons.Default.Warning },
        title = {
            Text(text = stringResource(R.string.delete_album))
        },
        text = {
            Text(text = stringResource(R.string.delete_album_dialog_txt))
        },
        onDismissRequest = {
            onDismiss
        },
        confirmButton = {
            Button(
                onClick = {
                    onDeleteAlbumConfirmed
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteAlbumDialogPreview() {

    DeleteAlbumDialog(
        onDismiss = {},
        onDeleteAlbumConfirmed = {}
    )
}