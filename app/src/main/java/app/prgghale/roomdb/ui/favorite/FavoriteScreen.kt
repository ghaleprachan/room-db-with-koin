package app.prgghale.roomdb.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.prgghale.roomdb.composables.NoDataLottie
import app.prgghale.roomdb.composables.UserProfileImg
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.extesion.Width
import app.prgghale.roomdb.ui.home.UserViewModel
import org.koin.compose.viewmodel.koinViewModel

@Preview(showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun FavoritePreView() {
    FavoriteContent(emptyList())
}

@Preview(showBackground = true)
@Composable
fun FavoriteItemPreView() {
    val user = UserTable(
        firstName = "Prachan",
        lastName = "Ghale",
        address = "Barpak-Gorkha",
        profession = "Mobile Application Developer",
    )
    FavoriteItem(user = user)
}

@Composable
fun FavoriteScreen(
    userViewModel: UserViewModel = koinViewModel() // Changed to koinViewModel
) {
    val favUserState by userViewModel.favoriteUsers.collectAsState()
    var isFirstTime by remember { mutableStateOf(true) }
    if (isFirstTime) {
        isFirstTime = false
        userViewModel.getFavoriteUsers()
    }

    FavoriteContent(
        favUsers = favUserState
    )
}

@Composable
private fun FavoriteContent(favUsers: List<UserTable>?) {
    if (favUsers.isNullOrEmpty()) {
        NoDataLottie()
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
            content = {
                items(favUsers.orEmpty()) {
                    FavoriteItem(user = it)
                }
            }
        )
    }
}

@Composable
private fun FavoriteItem(user: UserTable) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .clip(shape = CutCornerShape(topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserProfileImg(
            firstName = user.firstName,
            lastName = user.lastName
        )
        16.Width()
        Text(
            text = user.displayName(),
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
}


