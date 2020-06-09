package io.rot.labs.projectconf.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.utils.common.Resource
import io.rot.labs.projectconf.utils.network.ErrorMessage
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import java.net.HttpURLConnection

abstract class BaseViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    val messageString = MutableLiveData<Resource<String>>()

    val messageStringId = MutableLiveData<Resource<Int>>()

    fun isConnectedToInternet() = networkHelper.isNetworkConnected()

    fun isConnectedToInternetWithMessage(): Boolean {
        return if (networkHelper.isNetworkConnected()) {
            true
        } else {
            messageStringId.postValue(Resource.error(R.string.network_internet_not_connected))
            false
        }
    }

    fun handleNetworkError(throwable: Throwable) {
        networkHelper.castToNetworkError(throwable).run {
            when (this.code) {
                0 -> messageString.postValue(Resource.error(this.message))
                1 -> messageStringId.postValue(Resource.error(R.string.network_could_not_connect))
                2 -> messageStringId.postValue(Resource.error(R.string.network_time_out_occurred))
                3 -> messageStringId.postValue(Resource.error(R.string.network_could_not_connect))
                HttpURLConnection.HTTP_NOT_FOUND -> messageString.postValue(
                    Resource.error(
                        ErrorMessage.NOT_FOUND
                    )
                )
                HttpURLConnection.HTTP_BAD_REQUEST -> messageString.postValue(
                    Resource.error(
                        ErrorMessage.BAD_REQUEST
                    )
                )
                HttpURLConnection.HTTP_BAD_GATEWAY -> messageString.postValue(
                    Resource.error(
                        ErrorMessage.INTERNAL_SERVER_ERROR
                    )
                )
            }
        }
    }

    abstract fun onCreate()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
