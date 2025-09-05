package app.prgghale.roomdb.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun UserProfileImg(
    modifier: Modifier = Modifier,
    firstName: String?,
    lastName: String?,
    contentDescription: String? = null,
) {
    val imageUrl = "https://ui-avatars.com/api/?name=${firstName?.trim() ?: ""}+${lastName?.trim() ?: ""}&background=random"
    val platformContext = LocalPlatformContext.current
    AsyncImage(
        model = ImageRequest.Builder(platformContext)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}