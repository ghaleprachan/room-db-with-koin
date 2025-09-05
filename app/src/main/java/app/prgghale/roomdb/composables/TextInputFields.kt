package app.prgghale.roomdb.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CustomTextField(
    value: MutableState<String>,
    label: String
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value.value,
        onValueChange = { value.value = it },
        label = {
            Text(text = label)
        },
        shape = RoundedCornerShape(16.dp)
    )
}
