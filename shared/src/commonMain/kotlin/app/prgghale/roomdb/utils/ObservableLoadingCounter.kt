package app.prgghale.roomdb.utils

import kotlinx.coroutines.flow.*
import kotlinx.atomicfu.atomic // 從 kotlinx.atomicfu 導入

class ObservableLoadingCounter {
    // 使用 kotlinx.atomicfu.atomic 創建一個原子性的 Int
    private val count = atomic(0) // 初始值為 0
    private val loadingState = MutableStateFlow(count.value) // 初始讀取 count.value

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        // 使用 incrementAndGet() 方法
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        // 使用 decrementAndGet() 方法
        loadingState.value = count.decrementAndGet()
    }
}

suspend fun <T> Flow<T>.collectInto(counter: ObservableLoadingCounter): Flow<T> {
    return onStart { counter.addLoader() }
        .onCompletion { counter.removeLoader() }
}