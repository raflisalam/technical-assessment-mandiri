package com.raflisalam.technicalassestment.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlin.text.orEmpty

suspend inline fun <T> startProcess(stateFlow: MutableStateFlow<ApiResult<T>>, crossinline block: () -> Flow<T>) = block
    .invoke()
    .onStart {
        stateFlow.update { _ -> ApiResult.Loading }
    }.catchException(stateFlow)
    .collect {
        stateFlow.update { _ ->
            ApiResult.Success(it)
        }
    }

fun <T> Flow<T>.catchException(stateFlow: MutableStateFlow<ApiResult<T>>): Flow<T> = catch {
    when (it) {
        is IllegalStateException -> {
            stateFlow.update { ApiResult.Default }
        }

        else -> {
            stateFlow.update { _ -> ApiResult.Error(it.message.orEmpty(), it) }
        }
    }
}