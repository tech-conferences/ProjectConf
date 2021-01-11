/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.rot.labs.projectconf.utils.network.ErrorMessage.BAD_REQUEST
import io.rot.labs.projectconf.utils.network.ErrorMessage.INTERNAL_SERVER_ERROR
import io.rot.labs.projectconf.utils.network.ErrorMessage.NOT_FOUND
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import javax.inject.Singleton

@Singleton
class NetworkDBHelperImpl(private val context: Context) : NetworkDBHelper {

    override fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val isConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.let {
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || it.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                )
            }
        } else {
            connectivityManager.activeNetworkInfo?.isConnected
        }
        return isConnected ?: false
    }

    override fun castToNetworkDbError(throwable: Throwable): NetworkDBError {
        return when (throwable) {
            is ConnectException -> NetworkDBError(1, ErrorMessage.NOT_ABLE_TO_CONNECT)
            is SocketTimeoutException -> NetworkDBError(2, ErrorMessage.TIME_OUT_OCCURRED)
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> NetworkDBError(
                        HttpURLConnection.HTTP_NOT_FOUND,
                        NOT_FOUND
                    )
                    HttpURLConnection.HTTP_BAD_REQUEST -> NetworkDBError(
                        HttpURLConnection.HTTP_BAD_REQUEST,
                        BAD_REQUEST
                    )
                    HttpURLConnection.HTTP_BAD_GATEWAY -> NetworkDBError(
                        HttpURLConnection.HTTP_BAD_GATEWAY,
                        INTERNAL_SERVER_ERROR
                    )
                    else -> NetworkDBError(message = throwable.message())
                }
            }
            else -> {
                if (throwable.message?.contains("Unable to resolve host") == true) {
                    NetworkDBError(3, ErrorMessage.NOT_ABLE_TO_CONNECT)
                } else {
                    NetworkDBError(message = throwable.message ?: "")
                }
            }
        }
    }
}
