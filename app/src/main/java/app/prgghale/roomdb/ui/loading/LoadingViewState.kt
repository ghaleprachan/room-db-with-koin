package app.prgghale.roomdb.ui.loading

import androidx.compose.runtime.Immutable
import app.prgghale.roomdb.data.domain.UserProfession
import app.prgghale.roomdb.data.table.ProfessionTable
import app.prgghale.roomdb.data.table.UserTable

@Immutable
data class LoadingViewState(
    val user: List<UserTable> = emptyList(),
    val professions: List<ProfessionTable> = emptyList(),
    val userProfessions: List<UserProfession> = emptyList(),
    val userRefreshing: Boolean = false,
    val professionsRefreshing: Boolean = false,
    val userProfRefreshing: Boolean = false,
) {
    val refreshing
        get() = userRefreshing || professionsRefreshing || userProfRefreshing

    companion object {
        val Empty = LoadingViewState()
    }
}