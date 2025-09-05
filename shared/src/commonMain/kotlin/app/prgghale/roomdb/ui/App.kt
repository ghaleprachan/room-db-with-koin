package app.prgghale.roomdb.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.prgghale.roomdb.composables.AppScaffold
import app.prgghale.roomdb.composables.BottomBar
import app.prgghale.roomdb.composables.BottomNavItems
import app.prgghale.roomdb.composables.Screens
import app.prgghale.roomdb.route.MainContent
import app.prgghale.roomdb.route.navigateNextScreen
import app.prgghale.roomdb.ui.loading.LoadingViewModel
import org.koin.compose.koinInject

@Composable
fun App(loadingViewmodel: LoadingViewModel = koinInject()) {
     val navController = rememberNavController()
     val navBackStack by navController.currentBackStackEntryAsState()
     val currentRoute = navBackStack?.destination?.route
     var title by remember { mutableStateOf(BottomNavItems.Home.label) }
     Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
     ) {
          // Outer Scaffold mainly for BottomBar
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
               content = { outerScaffoldPadding -> // Padding from the outer Scaffold (for BottomBar)
                    // AppScaffold for TopAppBar and main screen content area
                    AppScaffold(
                         modifier = Modifier.padding(outerScaffoldPadding), // Apply outer padding to AppScaffold
                         title = title,
                         onSearch = {
                              title = Screens.Search.label
                              navigateNextScreen(
                                   navController,
                                   Screens.Search.label
                              )
                         }
                    ) { appScaffoldContentPadding ->
                         MainContent(
                              navController = navController,
                              contentPaddingForNavHost = appScaffoldContentPadding,
                              loadingViewmodel
                         )
                    }
               }
          )
     }
}