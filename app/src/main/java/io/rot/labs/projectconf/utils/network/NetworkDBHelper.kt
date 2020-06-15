package io.rot.labs.projectconf.utils.network

interface NetworkDBHelper {

    fun isNetworkConnected(): Boolean

    fun castToNetworkDbError(throwable: Throwable): NetworkDBError
}
