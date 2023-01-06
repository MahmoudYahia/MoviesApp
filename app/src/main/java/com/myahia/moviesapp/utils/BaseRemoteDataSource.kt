package com.myahia.moviesapp.utils

import com.myahia.moviesapp.R
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

open class BaseRemoteDataSource {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>
    ): APIResult<T> {
        return safeApiResult(call)
    }

    suspend fun <T : Any> genericSafeApiCall(
        call: suspend () -> Response<T>
    ): APIResult<T> {
        return genericSafeApiResult(call)
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): APIResult<T> {
        var response: Response<T>? = null
        try {
            response = call.invoke()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return handleResultError(response, ex)
        }

        return if (response.isSuccessful) {
            APIResult.Success(response.body()!!)
        } else
            handleResultError(response)
    }

    private suspend fun <T : Any> genericSafeApiResult(call: suspend () -> Response<T>): APIResult<T> {
        var response: Response<T>? = null
        try {
            response = call.invoke()

        } catch (ex: Exception) {
            ex.printStackTrace()
            return handleResultError(response, ex)
        }

        return if (response.isSuccessful) {
            APIResult.Success(response.body()!!)
        } else
            handleResultError(response)
    }

    private fun <T> handleResultError(
        response: Response<T>?,
        exception: Exception? = null
    ): APIResult.Error {
        val error: ErrorTypes = if (exception != null) {
            when (exception) {
                is ConnectException -> ErrorTypes.ConnectError
                is SocketTimeoutException -> ErrorTypes.ConnectError
                else -> ErrorTypes.GeneralError(exception.localizedMessage.orEmpty())
            }
        } else {
            //handle here any response that isn't successful or doesn't have responseCode or responseMessage
            val responseCode = response?.code()
            if (responseCode == 401) {
                ErrorTypes.TokenExpiryError
            } else {
                ErrorTypes.GeneralError(
                    response?.errorBody()?.string().orEmpty(),
                    responseCode.toString()
                )
            }
        }

        return APIResult.Error(error)
    }

    protected fun <T : Any> getAPIResult(response: APIResult<T>): APIResult<T> {
        return when (response) {
            is APIResult.Success -> {
                if (response.data == null) {
                    val errorMessage = "Error"
                    return APIResult.Error(ErrorTypes.CustomError(UserMessage(resMessage = R.string.SomethingWentWrong)))
                }

                return APIResult.Success(response.data)
            }
            is APIResult.Error -> response
        }
    }

    protected fun <T : Any> getGenericAPIResult(response: APIResult<T>): APIResult<T> {
        return when (response) {
            is APIResult.Success ->
                return APIResult.Success(response.data)
            is APIResult.Error -> response
        }
    }

}

object NetworkResponseCodes {
    const val SuccessResponse = "200"
    const val FailedResponse = "0"
    const val TokenExpired = 401
}

sealed class ErrorTypes {
    object TokenExpiryError : ErrorTypes()
    class TokenError(val errCode: String, val errorMessage: UserMessage) : ErrorTypes()
    object ConnectError : ErrorTypes()
    class GeneralError(val errorMessage: String, val statusCode: String? = null) : ErrorTypes()
    class CustomError(var errorMessage: UserMessage) : ErrorTypes()
    class InActiveUserError(val errCode: String, val errorMessage: UserMessage) : ErrorTypes()
}

