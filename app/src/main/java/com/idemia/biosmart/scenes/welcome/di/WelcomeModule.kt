package com.idemia.biosmart.scenes.welcome.di

import com.idemia.biosmart.scenes.welcome.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module class WelcomeModule(val activity: WelcomeActivity){
    @Provides
    fun providesRouter(): WelcomeRoutingLogic = WelcomeRouter()

    @Provides
    fun providesPresenter(): WelcomePresentationLogic = WelcomePresenter(activity)

    @Provides
    fun providesInteractor(welcomePresentationLogic: WelcomePresentationLogic): WelcomeBusinessLogic =
        WelcomeInteractor(welcomePresentationLogic)
}

@Subcomponent(modules = [WelcomeModule::class])
interface WelcomeComponent {
    fun inject(activity: WelcomeActivity)
}