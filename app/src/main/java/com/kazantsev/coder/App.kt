package com.kazantsev.coder

import android.app.Application
import com.kazantsev.coder.di.AppComponent
import com.kazantsev.coder.di.AppModule
import com.kazantsev.coder.di.DaggerAppComponent

class App : Application() {

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        instance = this
        _appComponent = DaggerAppComponent.builder()
            .application(AppModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
        val component get() = instance.appComponent
    }
}