package uk.udemy.recordshop.data.remote

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failed<Nothing>(
        val message: String?,
        val code: Int?
    ) : Result<Nothing>()
    data class Exception<Nothing>(val exception: Throwable) : Result<Nothing>()
}