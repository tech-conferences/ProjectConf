package io.rot.labs.projectconf.utils.display

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.provider.CalendarContract
import android.view.View

object ScreenUtils : ScreenResourcesHelper {

    fun setStatusBarColorAccordingToSystem(activity: Activity) {
        var flags = activity.window.decorView.systemUiVisibility

        if (!isDarkThemeOn()) {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        }
        activity.window.decorView.systemUiVisibility = flags

    }

    override fun isDarkThemeOn(): Boolean {
        return (Resources.getSystem().configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    override fun isPortrait(): Boolean {
        return Resources.getSystem().configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }


}