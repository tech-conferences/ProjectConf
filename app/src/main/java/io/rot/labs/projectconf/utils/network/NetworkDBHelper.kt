/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.network

interface NetworkDBHelper {

    fun isNetworkConnected(): Boolean

    fun castToNetworkDbError(throwable: Throwable): NetworkDBError
}
