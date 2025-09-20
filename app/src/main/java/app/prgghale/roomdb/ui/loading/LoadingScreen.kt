package app.prgghale.roomdb.ui.loading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.ui.home.UserViewModel
import app.prgghale.roomdb.utils.UiStates

@Composable
fun LoadingScreen(loadingViewModel: LoadingViewModel) {
    val viewState by rememberFlowWithLifecycle(flow = loadingViewModel.myState)
        .collectAsState(initial = LoadingViewState.Empty)
    // val viewState = loadingViewModel.myState.collectAsState()

    /*LoadingContent(
        viewState = viewState,
        refresh = {
            loadingViewModel.refresh()
        }
    )*/
}

@Composable
private fun LoadingContent(viewState: State<LoadingViewState>, refresh: () -> Unit = {}) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        TextButton(onClick = refresh) {
            Text(text = "Refresh")
        }
        when (val data = viewState.value.professions) {
            is UiStates.Success -> {
                data.data?.forEach {
                    Text(text = "Profession ${it.professionName}")
                }
            }
            else -> {}
        }
        /*viewState.value.?. forEach {
            Text(text = "User ${it.displayName()}")
        }

        viewState.professions.data?.forEach {

        }

        viewState.userProfessions.data?.forEach {
            Text(text = "User Profession ${it.user.displayName()} ${it.profession.professionName}")
        }*/

        if (viewState.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}