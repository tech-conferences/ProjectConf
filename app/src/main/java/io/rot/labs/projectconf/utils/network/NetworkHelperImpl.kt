package io.rot.labs.projectconf.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.utils.network.ErrorMessage.BAD_REQUEST
import io.rot.labs.projectconf.utils.network.ErrorMessage.INTERNAL_SERVER_ERROR
import io.rot.labs.projectconf.utils.network.ErrorMessage.NOT_FOUND
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import javax.inject.Singleton

@Singleton
class NetworkHelperImpl(private val context: Context) : NetworkHelper {

    override fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        }
        return isConnected ?: false

    }

    override fun castToNetworkError(throwable: Throwable): NetworkError {
        return when (throwable) {
            is ConnectException -> NetworkError(1, ErrorMessage.NOT_ABLE_TO_CONNECT)
            is SocketTimeoutException -> NetworkError(2, ErrorMessage.TIME_OUT_OCCURRED)
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> NetworkError(
                        HttpURLConnection.HTTP_NOT_FOUND,
                        NOT_FOUND
                    )
                    HttpURLConnection.HTTP_BAD_REQUEST -> NetworkError(
                        HttpURLConnection.HTTP_BAD_REQUEST,
                        BAD_REQUEST
                    )
                    HttpURLConnection.HTTP_BAD_GATEWAY -> NetworkError(
                        HttpURLConnection.HTTP_BAD_GATEWAY,
                        INTERNAL_SERVER_ERROR
                    )
                    else -> NetworkError()
                }
            }
            else -> {
                if (throwable.message?.contains("Unable to resolve host") == true) {
                    NetworkError(3, ErrorMessage.NOT_ABLE_TO_CONNECT)
                } else {
                    NetworkError()
                }
            }
        }
    }
}