package app.prgghale.roomdb.ui.userlist

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed


import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.prgghale.roomdb.composables.*
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.table.UserTable

import app.prgghale.roomdb.ui.home.UserViewModel
import app.prgghale.roomdb.utils.UiStates
import org.koin.compose.viewmodel.koinViewModel


@Composable
private fun UsersPreview() {
    UsersContent(users = emptyList(), {}, {})
}

@Composable
fun UsersScreen(usersViewModel: UserViewModel = koinViewModel()) {
//    val context = LocalContext.current

    val usersState = usersViewModel.userProfession.collectAsState()
    val deleteState = usersViewModel.delete.collectAsState()
    val updateState = usersViewModel.updateTable.collectAsState()

    val getData = remember { mutableStateOf(true) }
    var showAlertState by remember {
        mutableStateOf<UserTable?>(null)
    }

    if (getData.value) {
        getData.value = false
        usersViewModel.getUserProfession()
    }
    when (
        val state = usersState.value
    ) {
        is UiStates.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiStates.Success -> {
            UsersContent(
                users = state.data,
                onDelete = {
                    showAlertState = it
                },
                addToFavorite = {
                    usersViewModel.updateTable(it)
                }
            )
        }

        is UiStates.Error -> {
//            context.toastS(state.message)
        }

        else -> {}
    }

    if (showAlertState != null)
        ShowDeleteDialog(
            onDismiss = { showAlertState = null },
            onDelete = {
                usersViewModel.deleteUser(user = showAlertState!!)
            }
        )

    when (
        val state = deleteState.value
    ) {
        is UiStates.Loading -> {
            // DO Nothing
        }

        is UiStates.Success -> {
            showAlertState = null
            usersViewModel.refreshUser()
        }

        is UiStates.Error -> {
//            context.toastS(state.message)
        }

        else -> {}
    }

    when (val state = updateState.value) {
        is UiStates.Loading -> {
            // DO Nothing
        }

        is UiStates.Success -> {
            usersViewModel.refreshUser()
        }

        is UiStates.Error -> {
//            context.toastS(state.message)
        }

        else -> {}
    }
}

@Composable
private fun UsersContent(
    users: List<UserProfession>?,
    onDelete: (user: UserTable) -> Unit,
    addToFavorite: (user: UserTable) -> Unit
) {
    Column {

        if (users.isNullOrEmpty()) {
//            NoDataLottie()//FIXME
        } else {
            LazyColumn(content = {
                itemsIndexed(users.orEmpty()) { _, user ->
                    UserItem(user = user, onDelete = onDelete, addToFavorite = addToFavorite)
                }
            })
        }
    }
}

@Composable
fun UserItem(
    user: UserProfession,
    onDelete: (user: UserTable) -> Unit,
    addToFavorite: (user: UserTable) -> Unit
) {
    Column {
        ListItem(
            headlineContent = {
                Column {
                    Text(
                        text = user.user.displayName(),
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray) // 示例 overline 樣式
                    )
                    Text(
                        text = user.profession.professionName.orEmpty(),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    )
                }
            },
            supportingContent = { // <--- 修改點：M2 的 secondaryText 放入這裡
                Text(
                    text = user.user.address.orEmpty(),
                    style = TextStyle(Color.LightGray)
                )
            },
            leadingContent = {
                //FIXME
//                UserProfileImg(
//                    firstName = user.user.firstName,
//                    lastName = user.user.lastName
//                )
            },
            trailingContent = {
                TrailingContent(
                    isFavorite = user.user.isFavorite,
                    onDelete = { onDelete(user.user) },
                    addToFavorite = { addToFavorite(user.user.copy(isFavorite = it)) }
                )
            },
            modifier = Modifier // 可以根據需要添加 modifier
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}


@Composable
private fun TrailingContent(
    isFavorite: Boolean,
    onDelete: () -> Unit,
    addToFavorite: (Boolean) -> Unit,
) {
    var dialogState by remember { mutableStateOf(false) }
    if (dialogState)
        ShowDialog {
            dialogState = !dialogState
        }

    var visibleContent by remember { mutableStateOf(false) }

    Row {
        AnimatedVisibility(
            visible = visibleContent,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Row {
                IconButton(onClick = { addToFavorite(!isFavorite) }) {
                    Icon(
                        if (isFavorite) app.prgghale.roomdb.iconFilled.Favorite else app.prgghale.roomdb.iconOutlined.FavoriteBorder,
                        contentDescription = "Favorite Icon"
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        app.prgghale.roomdb.iconOutlined.Delete,
                        contentDescription = "Delete Icon"
                    )
                }
            }
        }

        IconButton(onClick = { visibleContent = !visibleContent }) {
            Icon(
                if (visibleContent) app.prgghale.roomdb.iconFilled.ArrowForward else app.prgghale.roomdb.iconFilled.ArrowBack,
                contentDescription = null
            )
        }
    }
}