package io.rot.labs.projectconf.utils.network

interface NetworkHelper {

    fun isNetworkConnected() : Boolean

    fun castToNetworkError(throwable: Throwable): NetworkError

}