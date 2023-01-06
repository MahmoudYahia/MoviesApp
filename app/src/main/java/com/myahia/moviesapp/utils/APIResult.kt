package com.myahia.moviesapp.utils

sealed class APIResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : APIResult<T>()
    data class Error(val error: ErrorTypes) : APIResult<Nothing>()

    companion object {
        const val HTTP_NOT_FOUND_CODE = 404
    }
}
