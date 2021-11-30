package app.prgghale.roomdb.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.prgghale.roomdb.composables.AppScaffold
import app.prgghale.roomdb.composables.CustomTextField
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.extesion.Height
import app.prgghale.roomdb.extesion.toJson
import app.prgghale.roomdb.extesion.toastL
import app.prgghale.roomdb.extesion.toastS
import app.prgghale.roomdb.utils.UiStates
import org.koin.androidx.compose.getViewModel
import java.lang.Error

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreView() {
    HomeScreen()
}

@Composable
fun HomeScreen(
    userViewModel: UserViewModel = getViewModel()
) {
    val context = LocalContext.current
    val addUserState = userViewModel.addUser.observeAsState()
    val usersState = userViewModel.users.observeAsState()

    AppScaffold(title = "Register Users") {
        HomeScreenContent(
            addUser = { user ->
                userViewModel.addUser(user = user)
                userViewModel.getUsers()
            }
        )
    }

    when (val state = addUserState.value) {
        is UiStates.Loading -> {
            context.toastS("Adding users")
        }
        is UiStates.Success -> {
            // userViewModel.getUsers()
        }
        is UiStates.Error -> {
            context.toastS(state.message ?: "Failed to add user")
        }
    }

    when (val state = usersState.value) {
        is UiStates.Loading -> {
            context.toastS("Fetching users")
        }
        is UiStates.Error -> {
            context.toastS(state.message ?: "Failed to fetch users")
        }
        is UiStates.Success -> {
            context.toastS(state.data.toJson())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    addUser: (user: UserTable) -> Unit,
) {
    val formState by remember { mutableStateOf(UserFormState()) }
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        CustomTextField(value = formState.firstName, label = "Enter first name")
        15.Height()
        CustomTextField(value = formState.lastName, label = "Enter last name")
        15.Height()
        CustomTextField(value = formState.address, label = "Enter address name")
        20.Height()
        Button(onClick = {
            addUser(formState.userData)
        }, Modifier.align(Alignment.End), enabled = !formState.btnState) {
            Text(text = "Add User")
        }
    }
}

data class UserFormState(
    val firstName: MutableState<String> = mutableStateOf(""),
    val lastName: MutableState<String> = mutableStateOf(""),
    val address: MutableState<String> = mutableStateOf(""),
) {
    val userData
        get() : UserTable =
            UserTable(
                firstName = firstName.value,
                lastName = lastName.value,
                address = address.value
            )

    val btnState
        get() : Boolean {
            return firstName.value.isEmpty() || lastName.value.isEmpty() || address.value.isEmpty()
        }
}
