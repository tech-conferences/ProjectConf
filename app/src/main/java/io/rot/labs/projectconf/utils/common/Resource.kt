package io.rot.labs.projectconf.utils.common

data class Resource<out T> internal constructor(val data: T?, val status: Status) {
    companion object {
        fun <T> success(data: T? = null) = Resource(data, Status.SUCCESS)

        fun <T> error(data: T? = null) = Resource(data, Status.ERROR)

        fun <T> loading(data: T? = null) = Resource(data, Status.LOADING)

        fun <T> unknown(data: T? = null) = Resource(data, Status.UNKNOWN)
    }
}
