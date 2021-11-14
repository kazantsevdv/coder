package com.kazantsev.coder.model

sealed class AppState<T> {
    data class Success<T>(val data: T) : AppState<T>()
    data class Error<T>(val message: String) : AppState<T>()
    class UpdateError<T>(val message: String) : AppState<T>()
    data class Loading<T>(val progress: Int?) : AppState<T>()
    class Refresh<T> : AppState<T>()
}