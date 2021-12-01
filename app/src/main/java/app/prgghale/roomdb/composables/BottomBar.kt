package app.prgghale.roomdb.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DrawerDefaults.Elevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.prgghale.roomdb.iconFilled

enum class BottomNavItems(val label: String, val icons: ImageVector) {
    Home("Add User", iconFilled.Add),
    Bookings("Users List", iconFilled.AccountBox),
    Favorite("Favorite", iconFilled.Star),
}

@Composable
fun BottomBar(
    onItemClick: (type: String) -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = BottomNavItems.values()
    NavigationBar {
        items.forEachIndexed { index: Int, item: BottomNavItems ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
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