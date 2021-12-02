package app.prgghale.roomdb.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun UserProfileImg(
    modifier: Modifier = Modifier,
    firstName: String?,
    lastName: String?,
    contentDescription: String? = null,
) {
    Image(
        painter = rememberImagePainter(
            data = "https://ui-avatars.com/api/?name=${firstName}+${lastName}",
            builder = {
                crossfade(true)
                transformations(CircleCropTransformation())
            }),
        contentDescription = contentDescription,
        modifier = modifier.size(50.dp)
    )
}