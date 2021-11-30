package app.prgghale.roomdb.ui.userlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import app.prgghale.roomdb.composables.AppScaffold
import app.prgghale.roomdb.composables.ShowAlertDialog
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.extesion.toastS
import app.prgghale.roomdb.ui.home.UserViewModel
import app.prgghale.roomdb.utils.UiStates
import org.koin.androidx.compose.getViewModel

@Preview(showSystemUi = true)
@Composable
private fun UsersPreview() {
    UsersContent(users = emptyList(), onDelete = {})
}

@Composable
fun UsersScreen(usersViewModel: UserViewModel = getViewModel()) {
    val usersState = usersViewModel.users.observeAsState()
    val deleteState = usersViewModel.delete.observeAsState()
    val context = LocalContext.current
    val getData = remember { mutableStateOf(true) }
    var showAlertState by remember {
        mutableStateOf<UserTable?>(null)
    }

    if (getData.value) {
        usersViewModel.getUsers()
    }
    when (val state = usersState.value) {
        is UiStates.Loading -> {
            getData.value = false
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiStates.Success -> {
            UsersContent(users = state.data, onDelete = {
                showAlertState = it
            })
        }
        is UiStates.Error -> {
            context.toastS(state.message)
        }
    }

    if (showAlertState != null)
        ShowAlertDialog(
            onDismiss = { showAlertState = null },
            onDelete = {
                usersViewModel.deleteUser(user = showAlertState!!)
            }
        )

    when (val state = deleteState.value) {
        is UiStates.Loading -> {
            // DO Nothing
        }
        is UiStates.Success -> {
            showAlertState = null
            getData.value = true
            usersViewModel.resetDelete()
        }
        is UiStates.Error -> {
            context.toastS(state.message)
        }
    }
}

@Composable
private fun UsersContent(
    users: List<UserTable>?,
    onDelete: (user: UserTable) -> Unit
) {
    AppScaffold(title = "Registered Users") {
        LazyColumn(content = {
            itemsIndexed(users.orEmpty()) { _, user ->
                UserItem(user = user, onDelete = onDelete)
            }
        })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UserItem(user: UserTable, onDelete: (user: UserTable) -> Unit) {
    ListItem(
        text = {
            Text(
                text = user.displayName(),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            )
        },
        secondaryText = { Text(text = user.address.orEmpty()) },
        trailing = {
            IconButton(onClick = {
                onDelete(user)
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Button", tint = Color.Red)
            }
        }
    )
}