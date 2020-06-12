package io.rot.labs.projectconf.utils.common

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object Toaster {

    fun show(context: Context?, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 100)
            show()
        }
    }
}
