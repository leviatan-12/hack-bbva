package com.idemia.biosmart.di

import com.idemia.biosmart.BioSmartApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class AppModule(val app: BioSmartApplication) {
    @Provides @Singleton fun provideApp() = app
}