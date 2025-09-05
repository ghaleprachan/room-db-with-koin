package app.prgghale.roomdb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.prgghale.roomdb.theme.RoomDBTheme
import app.prgghale.roomdb.ui.loading.LoadingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val loadingViewmodel by viewModel<LoadingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBTheme {
                App(loadingViewmodel)
            }
        }
    }
}
