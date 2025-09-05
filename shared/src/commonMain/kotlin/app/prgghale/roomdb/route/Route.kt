package app.prgghale.roomdb.route

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.prgghale.roomdb.composables.BottomNavItems
import app.prgghale.roomdb.composables.Screens
import app.prgghale.roomdb.ui.favorite.FavoriteScreen
import app.prgghale.roomdb.ui.home.HomeScreen
import app.prgghale.roomdb.ui.loading.LoadingScreen
import app.prgghale.roomdb.ui.loading.LoadingViewModel
import app.prgghale.roomdb.ui.profile.ProfileScreen
import app.prgghale.roomdb.ui.search.SearchScreen
import app.prgghale.roomdb.ui.userlist.UsersScreen

fun navigateNextScreen( navController: NavHostController, label: String) {
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
fun MainContent( navController: NavHostController,  contentPaddingForNavHost: PaddingValues,loadingViewmodel: LoadingViewModel) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItems.Home.label,
        // The NavHost itself is padded to respect the AppScaffold's TopAppBar
        modifier = Modifier.padding(contentPaddingForNavHost)
    ) {
        composable(route = BottomNavItems.Home.label) {
            // HomeScreen should not need additional padding from AppScaffold,
            // as NavHost's modifier already handles it.
            HomeScreen() // No padding passed from here
        }
        composable(BottomNavItems.Bookings.label) {
            UsersScreen() // Assuming other screens also don't need explicit scaffold padding
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