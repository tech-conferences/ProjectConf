/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.background.cfpUrl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import io.rot.labs.projectconf.utils.common.URLOpener

class CFPUrlReceiver : BroadcastReceiver() {

    companion object {
        const val URL_KEY = "url_key"
        const val NOTIFY_KEY = "notify_id"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val url = intent?.getStringExtra(URL_KEY)
        val notificationId = intent?.getIntExtra(NOTIFY_KEY, -1)
        URLOpener.openURL(url!!, false, context!!, true)
        NotificationManagerCompat.from(context).cancel(notificationId!!)
    }
}
