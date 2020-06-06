package io.rot.labs.projectconf.ui.events

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.model.EventBase
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder
import io.rot.labs.projectconf.utils.common.Topics
import kotlinx.android.synthetic.main.item_event.view.*
import kotlinx.android.synthetic.main.item_event_header.view.*

class EventBaseViewHolder(
    @LayoutRes layoutId: Int,
    parent: ViewGroup
) : BaseItemViewHolder<EventBase, EventBaseViewModel>(layoutId, parent) {

    override fun injectDependencies(buildComponent: ViewHolderComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(view: View) {
        itemView.ivSave?.setOnClickListener {
            // save to SavedItem Db
        }

        itemView.eventCardContainer?.setOnClickListener {
            // go to events details
        }
    }

    override fun setUpObservables() {
        super.setUpObservables()

        viewModel.eventPeriod.observe(this, Observer {
            it?.let {
                itemView.tvEventPeriod.text = it
            }
        })

        viewModel.topic.observe(this, Observer {
            it?.let {
                itemView.tvTopicTitle.text = it
                loadTopicDrawable(getTopicDrawableResId(it))
            }
        })

        viewModel.place.observe(this, Observer {
            it?.let {
                itemView.tvCityCountry.text = it
            }
        })

        viewModel.name.observe(this, Observer {
            it?.let {
                itemView.tvConfName.text = it
            }
        })

        viewModel.day.observe(this, Observer {
            it?.let {
                itemView.tvStartDate.text = it
            }
        })

    }

    private fun loadTopicDrawable(@DrawableRes drawableRes: Int) {
        Glide.with(itemView.context)
            .load(drawableRes)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(itemView.ivTopic)
    }

    @DrawableRes
    private fun getTopicDrawableResId(topic: String): Int {
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