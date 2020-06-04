package io.rot.labs.projectconf.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.view.doOnLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.di.component.DaggerViewHolderComponent
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.di.module.ViewHolderModule
import javax.inject.Inject


/**
 * LifeCycle aware ViewHolder
 * This class provides convenient methods to handle its lifecycle
 *
 * If viewholder is in Paused state, data won't be updated in this viewholder
 */
abstract class BaseItemViewHolder<T : Any, VM : BaseItemViewModel<T>>(
    @LayoutRes layoutId: Int, parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)),
    LifecycleOwner {

    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var lifecycleRegistry: LifecycleRegistry

    init {
        onCreate()
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    /**
     * Marks LifeCycle Created for this ViewHolder
     */
    fun onCreate() {
        injectDependencies(getBuildComponent())
        lifecycleRegistry.markState(Lifecycle.State.INITIALIZED)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
        setUpObservables()
        setupView(itemView)
    }

    /**
     * Marks LifeCycle Started -> Resumed for this ViewHolder
     * For Example : when viewholders are visible on screen then there is state is Resumed
     */
    fun onStart() {
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
        lifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    /**
     * Marks LifeCycle Started -> Created for this ViewHolder
     * For Example : when viewholders are not visible on screen then there is state is Stopped
     */
    fun onStop() {
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    /**
     * Marks LifeCycle Destroyed for this ViewHolder
     */
    fun onDestroy() {
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        viewModel.onManualCleared()
    }

    fun bind(data: T) {
        viewModel.updateData(data)
    }

    protected open fun setUpObservables() {
        viewModel.messageString.observe(this, Observer {
            it.data?.let { showMessage(it) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.let { showMessage(it) }
        })
    }

    private fun getBuildComponent() = DaggerViewHolderComponent
        .builder()
        .applicationComponent((itemView.context.applicationContext as ConfApplication).appComponent)
        .viewHolderModule(ViewHolderModule(this))
        .build()

    fun showMessage(message: String) =
        Toast.makeText(itemView.context, message, Toast.LENGTH_LONG).show()

    fun showMessage(@StringRes messageId: Int) = showMessage(itemView.context.getString(messageId))

    abstract fun injectDependencies(buildComponent: ViewHolderComponent)

    abstract fun setupView(view: View)
}