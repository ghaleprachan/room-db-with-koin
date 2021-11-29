package app.prgghale.roomdb

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class BottomNavItems(val label: String, val icons: ImageVector) {
    Home("Home", iconFilled.Home),
    Bookings("Bookings", iconFilled.AccountBox),
    History("History", iconFilled.Close),
    Notification("Notification", iconFilled.Notifications),
    Profile("Profile", iconFilled.Person),
}

@Composable
fun BottomBar() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = BottomNavItems.values()
    NavigationBar {
        items.forEachIndexed { index: Int, item: BottomNavItems ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    // selectedItem = index
                },
                icon = {
                    IconButton(
                        onClick = { selectedItem = index },
                        modifier = Modifier.height(30.dp)
                    ) {
                        Icon(item.icons, contentDescription = "")
                    }
                },
                colors = NavigationBarItemDefaults.colors()
            )
        }
    }
}