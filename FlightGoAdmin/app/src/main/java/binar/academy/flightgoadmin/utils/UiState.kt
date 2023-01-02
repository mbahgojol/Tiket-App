package binar.academy.flightgoadmin.utils

sealed class UiState<out T : Any?> {
    object Loading : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
}

val UiState<Nothing>.Empty: UiState.Success<Unit>
    get() = UiState.Success(Unit)