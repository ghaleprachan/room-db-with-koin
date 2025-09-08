package app.prgghale.roomdb.composables

import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(val label: String, val icons: ImageVector) {
    Home("Add User", app.prgghale.roomdb.iconFilled.Add),
    Bookings("Users List", app.prgghale.roomdb.iconFilled.AccountBox),
    Favorite("Favorite", app.prgghale.roomdb.iconFilled.Star),
    Loading("Loading", app.prgghale.roomdb.iconFilled.Downloading),
    Profile("Profile", app.prgghale.roomdb.iconFilled.Person),
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