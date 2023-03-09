package com.undefined.data.utils

import com.undefined.domain.UIState
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

fun Throwable.error(): UIState.GenericError {
    return when (this) {
        is CancellationException ->
            UIState.GenericError(null, null)
        is IOException -> {
            UIState.GenericError(null, "Connection error !")
        }
        is HttpException -> {
            try {
                if (response()?.code() == 401) {
                    // todo: catch unauthorized request error
                    return UIState.GenericError(null, null)
                }
                val json = JSONObject(response()?.errorBody()?.charStream()?.readText() ?: "")
                UIState.GenericError(
                    if (json.isNull("data"))
                        null
                    else
                        json.getString("data"),
                    json.getString("message")
                )
            } catch (e: Exception) {
                e.printStackTrace()
                UIState.GenericError(null, "Unknown error")
            }
        }
        else ->
            UIState.GenericError(null, "Unknown error")
    }
}