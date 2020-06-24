package io.rot.labs.projectconf.utils.display

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import io.rot.labs.projectconf.data.local.prefs.ThemePreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreenUtils @Inject constructor(val themePrefs: ThemePreferences) : ScreenResourcesHelper {

    override fun setStatusBarColor(activity: Activity) {
        var flags = activity.window.decorView.systemUiVisibility

        val mode = themePrefs.getThemeMode()

        if ((mode == AppCompatDelegate.MODE_NIGHT_NO || (!isDarkThemeOn() && mode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)) &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        ) {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.statusBarColor = Color.WHITE
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            activity.window.statusBarColor = Color.BLACK
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
