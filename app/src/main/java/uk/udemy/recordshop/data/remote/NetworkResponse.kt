package uk.udemy.recordshop.data.remote

interface NetworkResponse<out Data, out ServerErrorResponse> {
    data class Success<Data>(val data: Data) : NetworkResponse<Data, Nothing>
    data class Failure<ServerErrorResponse>(
        val  message: String?,
        val code: Int?,
        val failureResponse: ServerErrorResponse?
    ) : NetworkResponse<Nothing, ServerErrorResponse>

    data class Exception(val exception: Throwable) : NetworkResponse<Nothing, Nothing>
}