package uk.udemy.recordshop.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uk.udemy.recordshop.R

// The Dialog Alert for deleting albums
@Composable
fun DeleteAlbumDialog(
    onDismiss: () -> Unit,
    onDeleteAlbumConfirmed: () -> Unit
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
            onDismiss()
        },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                onClick = {
                    onDeleteAlbumConfirmed()
                }
            ) {
                Text(stringResource(R.string.yes))
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.no))
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