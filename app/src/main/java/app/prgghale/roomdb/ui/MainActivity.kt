package app.prgghale.roomdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.prgghale.roomdb.composables.BottomBar
import app.prgghale.roomdb.composables.BottomNavItems
import app.prgghale.roomdb.theme.RoomDBTheme
import app.prgghale.roomdb.ui.home.HomeScreen
import app.prgghale.roomdb.ui.userlist.UsersScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBTheme {
                val navController = rememberNavController()
                val navBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStack?.destination?.route

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomBar(
                                onItemClick = {
                                    if (currentRoute != it) {
                                        navController.navigate(it) {
                                            navController.graph.startDestinationRoute?.let { route ->
                                                popUpTo(route = route) {
                                                    saveState = true
                                                }
                                            }
                                            restoreState = true
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        },
                        content = { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                NavHost(
                                    navController = navController,
                                    startDestination = BottomNavItems.Home.label
                                ) {
                                    composable(route = BottomNavItems.Home.label) {
                                        HomeScreen()
                                    }
                                    composable(BottomNavItems.Bookings.label) {
                                        UsersScreen()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
