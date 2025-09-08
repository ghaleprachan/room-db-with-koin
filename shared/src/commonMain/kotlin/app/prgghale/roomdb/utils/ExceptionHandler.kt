package app.prgghale.roomdb.utils

inline fun <T> handleTryCatch(task: () -> UiStates<T>): UiStates<T> {
    return try {
        task.invoke()
    } catch (ex: Exception) {
        UiStates.Error(message = ex.message)
    }
}