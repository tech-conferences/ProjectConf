package io.rot.labs.projectconf.ui.eventsItem

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventHeader
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.ui.eventsList.EventsListActivity
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.common.Topics

object EventsItemHelper {

    fun transformToInterleavedList(list: List<EventEntity>): List<EventItem> {
        val periodEventListMap = mutableMapOf<String, MutableList<EventEntity>>()

        list.forEach {
            val eventPeriod = TimeDateUtils.getEventPeriod(it.event.startDate)
            var eventList = periodEventListMap[eventPeriod]
            if (eventList == null) {
                eventList = mutableListOf()
                eventList.add(it)
                periodEventListMap[eventPeriod] = eventList
            } else {
                eventList.add(it)
            }
        }

        val result = mutableListOf<EventItem>()

        periodEventListMap.forEach {
            result.add(EventHeader(it.key))
            it.value.forEach { event ->
                result.add(event)
            }
        }

        return result
    }

    @DrawableRes
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

    fun navigateToEventsListActivity(
        context: Context,
        topicTitle: String,
        topicSub: String,
        list: ArrayList<String>
    ) {
        context.startActivity(Intent(context, EventsListActivity::class.java).apply {
            putExtra(
                EventsListActivity.TOPIC_TITLE,
                topicTitle
            )
            putExtra(
                EventsListActivity.TOPIC_SUB,
                topicSub
            )
            putExtra(
                EventsListActivity.TOPICS_LIST,
                list
            )
        })
    }
}
