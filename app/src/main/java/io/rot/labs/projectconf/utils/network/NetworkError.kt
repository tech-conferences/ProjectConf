package io.rot.labs.projectconf.utils.network

data class NetworkError(
    val code: Int = 0,
    val message: String = "Oops, Something went wrong, please check Network connection"
)