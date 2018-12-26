package com.idemia.biosmart.di

import com.idemia.biosmart.BioSmartApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: BioSmartApplication)
}