package app.prgghale.roomdb.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ShowAlertDialog(
    onDismiss: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDelete()
            }) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete User") },
        text = { Text(text = "Do you want to delete user?") },
        icon = { Icon(Icons.Default.Delete, contentDescription = null) }
    )
}