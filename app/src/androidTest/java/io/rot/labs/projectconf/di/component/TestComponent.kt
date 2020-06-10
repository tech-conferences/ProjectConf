package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.module.ApplicationTestModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationTestModule::class])
interface TestComponent : ApplicationComponent
