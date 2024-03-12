package ru.nurdaulet.dummyproducts.application

import android.app.Application
import ru.nurdaulet.dummyproducts.di.DaggerApplicationComponent

class DummyApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}