package uk.udemy.recordshop.data.remote

import kotlin.Result

sealed interface NetworkResponse<out Data> {
    data class Success<Data>(val data: Data) : NetworkResponse<Data>
    data class Failure<Nothing>(
        val  message: String?,
        val code: Int?,
    ) : NetworkResponse<Nothing>

    data class Exception(val exception: Throwable) : NetworkResponse<Nothing>
}