package app.prgghale.roomdb.ui.loading

import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicInteger


class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

suspend fun <T> Flow<T>.collectInto(counter: ObservableLoadingCounter): Flow<T> {
    return onStart { counter.addLoader() }
        .onCompletion { counter.removeLoader() }
}