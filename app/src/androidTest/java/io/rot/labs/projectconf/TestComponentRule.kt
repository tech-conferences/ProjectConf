package io.rot.labs.projectconf

import android.content.Context
import io.rot.labs.projectconf.di.component.DaggerActivityComponent
import io.rot.labs.projectconf.di.component.DaggerTestComponent
import io.rot.labs.projectconf.di.component.TestComponent
import io.rot.labs.projectconf.di.module.ApplicationTestModule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestComponentRule(private val context: Context) : TestRule {

    var testComponent: TestComponent? = null

    fun getContext() = context

    fun setupDaggerTestComponentInApplication() {
        val application = context.applicationContext as ConfApplication
        testComponent = DaggerTestComponent.builder()
            .applicationTestModule(ApplicationTestModule(application))
            .build()
        application.setComponent(testComponent!!)

    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                try {
                    setupDaggerTestComponentInApplication()
                    base.evaluate()
                } finally {
                    testComponent = null
                }
            }
        }
    }


}