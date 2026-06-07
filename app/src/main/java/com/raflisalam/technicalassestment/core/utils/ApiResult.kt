package com.raflisalam.technicalassestment.core.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : ApiResult<Nothing>()
    data object Default : ApiResult<Nothing>()
}

inline fun <T> ApiResult<T>.onLoading(action: () -> Unit): ApiResult<T> {
    if (this is ApiResult.Loading) action()
    return this
}

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(data)
    return this
}

inline fun <T> ApiResult<T>.onError(action: (message: String, cause: Throwable?) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) action(message, cause)
    return this
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        val message = when (e.code()) {
            401 -> "Unauthorized: API key tidak valid."
            404 -> "Data tidak ditemukan."
            429 -> "Terlalu banyak request. Coba lagi nanti."
            in 500..599 -> "Server sedang bermasalah. Coba lagi nanti."
            else -> "Terjadi kesalahan HTTP (${e.code()})."
        }
        ApiResult.Error(message, e)
    } catch (e: UnknownHostException) {
        ApiResult.Error("Tidak ada koneksi internet.", e)
    } catch (e: SocketTimeoutException) {
        ApiResult.Error("Koneksi timeout. Periksa jaringan kamu.", e)
    } catch (e: IOException) {
        ApiResult.Error("Gagal terhubung ke server.", e)
    } catch (e: Exception) {
        ApiResult.Error("Terjadi kesalahan: ${e.localizedMessage}", e)
    }
}
