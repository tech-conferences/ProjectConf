/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.display

import android.app.Activity

interface ScreenResourcesHelper {

    fun isDarkThemeOn(): Boolean

    fun isPortrait(): Boolean

    fun setStatusBarColor(activity: Activity)
}
