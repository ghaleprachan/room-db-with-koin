package app.prgghale.roomdb.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import app.prgghale.roomdb.composables.ShowDialog
import app.prgghale.roomdb.extesion.Height
import app.prgghale.roomdb.ui.loading.rememberFlowWithLifecycle
import app.prgghale.roomdb.ui.userlist.UserItem
import org.koin.compose.viewmodel.koinViewModel
@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()) {
    val viewState by rememberFlowWithLifecycle(flow = viewModel.state)
        .collectAsState(initial = SearchViewState.Empty)

    SearchContent(
        state = viewState,
        action = { action ->
            when (action) {
                is SearchAction.OpenShowDetails -> {}
                is SearchAction.Search -> {
                    viewModel.submitAction(action = action)
                }
            }
        }
    )
}

@Composable
private fun SearchContent(state: SearchViewState, action: (SearchAction) -> Unit) {
    var searchValue by remember { mutableStateOf(TextFieldValue(state.query)) }
    var dialogState by remember { mutableStateOf(false) }

    Column {
        16.Height()
        TextField(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(shape = CircleShape),
            value = searchValue,
            onValueChange = {
                searchValue = it
                action.invoke(SearchAction.Search(searchTerm = it.text))
            },
            placeholder = { Text(text = "Search user", color = Color(0xFF666262)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF5F4F4),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
        )
        16.Height()
        LazyColumn(content = {
            items(state.searchResult) {
                UserItem(
                    user = it,
                    onDelete = {
                        dialogState = !dialogState
                    },
                    addToFavorite = {
                        dialogState = !dialogState
                    }
                )
            }
        })
    }

    if (dialogState)
        ShowDialog(message = "Ui on user list and search screen is made with different functionality. Working on to improve reuse.") {
            dialogState = !dialogState
        }
}