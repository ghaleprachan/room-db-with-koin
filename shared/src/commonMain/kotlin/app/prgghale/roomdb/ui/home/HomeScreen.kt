package app.prgghale.roomdb.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.prgghale.roomdb.composables.CustomTextField
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.extesion.Height

import app.prgghale.roomdb.utils.UiStates
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomePreView() {
    HomeScreen()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    userViewModel: UserViewModel = koinViewModel(),
) {
    val onCreate = remember { mutableStateOf(true) }

//    val context = LocalContext.current
    val keyboardState = LocalFocusManager.current
    // val state = LocalSoftwareKeyboardController.current

    val addUserState by userViewModel.addUser.collectAsState()
    var formState by remember { mutableStateOf(UserFormState()) }

    val professions = userViewModel.professions.collectAsState()


    HomeScreenContent(
        formState = formState,
        addUser = { user ->
            userViewModel.addUser(user = user)
        },
        professions = professions.value
    )

    when (val state = addUserState) {
        is UiStates.Loading -> {  /*DO Nothing*/
        }
        is UiStates.Success -> {
            keyboardState.clearFocus()
            formState = UserFormState()
        }
        is UiStates.Error -> {
//            context.toastS(state.message ?: "Failed to add user")//FIXME
        }
        else -> {}
    }

    if (onCreate.value) {
        userViewModel.getProfessions()
    }
}

// FIXME change way of using professions on ui
@OptIn(ExperimentalMaterial3Api::class,)
@Composable
private fun HomeScreenContent(
    addUser: (user: UserTable) -> Unit,
    formState: UserFormState,
    professions: List<ProfessionTable>,
) {
    var dropDownState by remember { mutableStateOf(false) }
    var selectedProfession by remember { mutableStateOf<ProfessionTable?>(null) }

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
        15.Height()
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = selectedProfession != null,
                    enter = expandHorizontally(),
                    exit = shrinkHorizontally()
                ) {
                    Text(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp),
                        text = selectedProfession?.professionName.orEmpty(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(color = Color.DarkGray, fontSize = 16.sp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { dropDownState = !dropDownState },
                ) {
                    Text(text = "Select profession")
                }
            }


            DropdownMenu(
                expanded = dropDownState,
                onDismissRequest = {
                    dropDownState = !dropDownState
                }
            ) {
                professions.forEach { profession -> // 更改變數名稱以避免與外部 selectedProfession 混淆
                    DropdownMenuItem(
                        text = { Text(text = profession.professionName.orEmpty()) }, // <--- 修改點：將 Text 放入 text 參數
                        onClick = {
                            dropDownState = !dropDownState
                            selectedProfession = profession
                        }
                    )
                }
            }
        }
        20.Height()
        Button(
            onClick = {
                addUser(formState.userData.copy(profession = selectedProfession?.professionId))
            },
            modifier = Modifier.align(Alignment.End),
            enabled = !formState.btnState && selectedProfession != null
        ) {
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
                address = address.value,
                profession = null
            )

    val btnState
        get() : Boolean {
            return firstName.value.isEmpty() || lastName.value.isEmpty() || address.value.isEmpty()
        }
}
