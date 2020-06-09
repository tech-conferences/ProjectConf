package io.rot.labs.projectconf.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory<T : ViewModel>(
    private val kClass: KClass<T>,
    private val creator: () -> T
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(kClass.java)) {
            return creator() as T
        } else {
            throw IllegalArgumentException("Unknown Class Name : ${kClass.qualifiedName}")
        }
    }
}
