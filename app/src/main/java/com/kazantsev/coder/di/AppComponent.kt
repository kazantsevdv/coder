package com.kazantsev.coder.di

import com.kazantsev.coder.view.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        DbModule::class,
        RepoModule::class,
        ImageModule::class
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