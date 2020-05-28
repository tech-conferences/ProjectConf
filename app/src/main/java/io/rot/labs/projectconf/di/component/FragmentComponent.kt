package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.FragmentScope
import io.rot.labs.projectconf.di.module.FragmentModule


@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {
}