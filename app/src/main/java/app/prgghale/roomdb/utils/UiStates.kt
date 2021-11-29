package app.prgghale.roomdb.utils

sealed class UiStates<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean = false
) {
    class Success<T>(data: T?) : UiStates<T>(data = data, isLoading = false)
    class Error<T>(message: String?) : UiStates<T>(message = message, isLoading = false)
    class Loading<T>(isLoading: Boolean = true) : UiStates<T>(isLoading = isLoading)
}