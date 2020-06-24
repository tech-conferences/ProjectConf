package io.rot.labs.projectconf.utils.display

import android.app.Activity

interface ScreenResourcesHelper {

    fun isDarkThemeOn(): Boolean

    fun isPortrait(): Boolean

    fun setStatusBarColor(activity: Activity)
}
