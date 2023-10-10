package app.prgghale.roomdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.prgghale.roomdb.composables.AppScaffold
import app.prgghale.roomdb.composables.BottomBar
import app.prgghale.roomdb.composables.BottomNavItems
import app.prgghale.roomdb.composables.Screens
import app.prgghale.roomdb.theme.RoomDBTheme
import app.prgghale.roomdb.ui.favorite.FavoriteScreen
import app.prgghale.roomdb.ui.home.HomeScreen
import app.prgghale.roomdb.ui.loading.LoadingScreen
import app.prgghale.roomdb.ui.loading.LoadingViewModel
import app.prgghale.roomdb.ui.profile.ProfileScreen
import app.prgghale.roomdb.ui.search.SearchScreen
import app.prgghale.roomdb.ui.userlist.UsersScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val loadingViewmodel by viewModel<LoadingViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBTheme {
                val navController = rememberNavController()
                val navBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStack?.destination?.route
                var title by remember { mutableStateOf(BottomNavItems.Home.label) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomBar(
                                currentRoute = currentRoute,
                                onItemClick = {
                                    title = it
                                    navigateNextScreen(navController, it)
                                }
                            )
                        },
                        content = { innerPadding ->
                            AppScaffold(
                                modifier = Modifier.padding(innerPadding),
                                title = title,
                                onSearch = {
                                    title = Screens.Search.label
                                    navigateNextScreen(
                                        navController,
                                        Screens.Search.label
                                    )
                                }
                            ) {
                                MainContent(navController)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun navigateNextScreen(
        navController: NavHostController,
        label: String
    ) {
        navController.navigate(label) {
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route = route) {
                    saveState = true
                }
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    @Composable
    private fun MainContent(navController: NavHostController) {
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
            composable(BottomNavItems.Favorite.label) {
                FavoriteScreen()
            }
            composable(Screens.Search.label) {
                SearchScreen()
            }
            composable(BottomNavItems.Loading.label) {
                LoadingScreen(loadingViewModel = loadingViewmodel)
            }
            composable(BottomNavItems.Profile.label) {
                ProfileScreen()
            }
        }
    }
}
