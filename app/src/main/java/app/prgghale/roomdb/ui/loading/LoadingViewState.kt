package app.prgghale.roomdb.ui.loading

import androidx.compose.runtime.Immutable
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable
import app.prgghale.roomdb.utils.UiStates

@Immutable
data class LoadingViewState(
    val user: UiStates<List<UserTable>> = UiStates.Loading(),
    val professions: UiStates<List<ProfessionTable>> = UiStates.Loading(),
    val userProfessions: UiStates<List<UserProfession>> = UiStates.Loading(),
) {
    companion object {
        val Empty = LoadingViewState()
    }

    val isLoading =
        userProfessions is UiStates.Loading || professions is UiStates.Loading || user is UiStates.Loading

}