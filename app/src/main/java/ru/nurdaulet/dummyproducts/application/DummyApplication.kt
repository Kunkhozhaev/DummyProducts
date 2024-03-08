package ru.nurdaulet.dummyproducts.application

import android.app.Application
import android.content.Context
import ru.nurdaulet.dummyproducts.di.DaggerApplicationComponent

class DummyApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        //For debug purposes
        lateinit var instance: DummyApplication
            private set

        fun getApplicationContext(): Context? {
            return instance.applicationContext
        }
    }
}