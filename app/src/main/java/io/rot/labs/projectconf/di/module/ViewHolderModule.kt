package io.rot.labs.projectconf.di.module

import androidx.lifecycle.LifecycleRegistry
import dagger.Module
import dagger.Provides
import io.rot.labs.projectconf.di.ViewModelScope
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    @ViewModelScope
    fun provideLifecycleRegistry() = LifecycleRegistry(viewHolder)
}
