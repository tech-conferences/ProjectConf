package io.rot.labs.projectconf.utils.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import io.rot.labs.projectconf.R

object URLOpener {

    @JvmStatic
    fun openURL(url: String, isTwitter: Boolean, context: Context, isBackground: Boolean = false) {
        val urlIntent = CustomTabsIntent.Builder().apply {
            val colorId = if (isTwitter) {
                R.color.blueTwitter
            } else {
                R.color.yellow500
            }
            setToolbarColor(ContextCompat.getColor(context, colorId))
            addDefaultShareMenuItem()
            setShowTitle(true)
        }.build()

        if (isBackground) {
            urlIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        urlIntent.launchUrl(context, Uri.parse(url))
    }
}
