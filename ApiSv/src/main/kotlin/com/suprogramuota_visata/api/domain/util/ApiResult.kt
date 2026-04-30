package com.suprogramuota_visata.api.domain.util

/**
 * A generic class that holds a value with its loading status or an error.
 * This pattern ensures that ViewModels don't crash from unhandled exceptions.
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val exception: Throwable? = null) : ApiResult<Nothing>()
}

inline fun <T> safeApiCall(apiCall: () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: Exception) {
        // Catch exceptions (like networking errors, parsing errors, etc.)
        ApiResult.Error(e.message ?: "An unknown error occurred", e)
    }
}
