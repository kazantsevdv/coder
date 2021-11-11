package com.kazantsev.coder.di

import android.app.Application
import com.kazantsev.coder.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        DbModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: MainActivity)


    @Component.Builder
     interface Builder {

        fun build(): AppComponent


       fun application(application: AppModule): Builder
    }

}