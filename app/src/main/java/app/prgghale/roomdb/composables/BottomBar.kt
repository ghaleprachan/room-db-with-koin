package app.prgghale.roomdb.composables

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import app.prgghale.roomdb.iconFilled

enum class BottomNavItems(val label: String, val icons: ImageVector) {
    Home("Add User", iconFilled.Add),
    Bookings("Users List", iconFilled.AccountBox),
    Favorite("Favorite", iconFilled.Star),
    Loading("Loading", iconFilled.Downloading),
    Profile("Profile", iconFilled.Person),
}

enum class Screens(val label: String) {
    Search("Search")
}

@Composable
fun BottomBar(
    onItemClick: (type: String) -> Unit = {},
    currentRoute: String?
) {
    val items = BottomNavItems.values()
    NavigationBar {
        items.forEachIndexed { index: Int, item: BottomNavItems ->
            NavigationBarItem(
                selected = currentRoute == items[index].label,
                onClick = {
                    onItemClick(item.label)
                },
                label = { Text(text = item.label) },
                icon = {
                    Icon(item.icons, contentDescription = "")
                },
                colors = NavigationBarItemDefaults.colors()
            )
        }
    }
}