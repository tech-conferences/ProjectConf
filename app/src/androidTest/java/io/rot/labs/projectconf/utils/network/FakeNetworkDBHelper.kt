/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

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
