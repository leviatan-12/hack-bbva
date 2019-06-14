package com.idemia.biosmart

import com.idemia.biosmart.scenes.welcome.di.WelcomeComponent
import com.idemia.biosmart.scenes.welcome.di.WelcomeModule
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    // Inject
    fun inject(app: BioSmartApplication)

    // Plus
    fun plus(module: WelcomeModule): WelcomeComponent
}

@Module class AppModule(val app: BioSmartApplication) {
    @Provides @Singleton fun provideApp() = app
}