package io.rot.labs.projectconf.utils.network

import android.content.Context

class FakeNetworkHelper(private val context: Context) : NetworkHelper {

    override fun isNetworkConnected(): Boolean {
        return true
    }

    override fun castToNetworkError(throwable: Throwable): NetworkError {
        return NetworkHelperImpl(context).castToNetworkError(throwable)
    }
}
