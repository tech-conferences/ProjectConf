package io.rot.labs.projectconf.utils.network

import android.content.Context

class FakeNetworkDBHelper(private val context: Context) : NetworkDBHelper {

    override fun isNetworkConnected(): Boolean {
        return true
    }

    override fun castToNetworkDbError(throwable: Throwable): NetworkDBError {
        return NetworkDBHelperImpl(context).castToNetworkDbError(throwable)
    }
}
