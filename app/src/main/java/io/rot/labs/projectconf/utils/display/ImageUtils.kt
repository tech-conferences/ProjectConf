package io.rot.labs.projectconf.utils.display

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.utils.common.Topics

object ImageUtils {

    @JvmStatic
    fun loadImageDrawable(context: Context, @DrawableRes resId: Int, targetView: ImageView) {
        Glide.with(context)
            .asBitmap()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .apply(RequestOptions().override(100))
            .load(resId)
            .into(targetView)
    }

    @DrawableRes
    @JvmStatic
    fun getTopicDrawableResId(topic: String): Int {
        return when (topic) {
            Topics.ANDROID -> R.drawable.android_logo
            Topics.CLOJURE -> R.drawable.clojure_logo
            Topics.CPP -> R.drawable.cpp_logo
            Topics.CSS -> R.drawable.css_logo
            Topics.DATA -> R.drawable.data_logo
            Topics.DEVOPS -> R.drawable.devops_logo
            Topics.DOTNET -> R.drawable.dot_net_logo
            Topics.ELIXIR -> R.drawable.elixir_logo
            Topics.ELM -> R.drawable.elm_logo
            Topics.GOLANG -> R.drawable.golang_logo
            Topics.GRAPHQL -> R.drawable.graphql_logo
            Topics.GROOVY -> R.drawable.groovy_logo
            Topics.IOS -> R.drawable.ios_logo
            Topics.IOT -> R.drawable.iot_logo
            Topics.JAVA -> R.drawable.java_logo
            Topics.JAVASCRIPT -> R.drawable.javascript_logo
            Topics.KOTLIN -> R.drawable.kotlin_logo
            Topics.PHP -> R.drawable.php_logo
            Topics.PYTHON -> R.drawable.python_logo
            Topics.RUBY -> R.drawable.ruby_logo
            Topics.RUST -> R.drawable.rust_logo
            Topics.SCALA -> R.drawable.scala_logo
            Topics.SECURITY -> R.drawable.security_logo
            Topics.TYPESCRIPT -> R.drawable.typescript_logo
            Topics.UX -> R.drawable.ux_logo
            else -> R.drawable.general_logo
        }
    }
}
