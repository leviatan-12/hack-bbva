package com.idemia.biosmart.scenes.welcome

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *  Welcome Interactor
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeInteractor : WelcomeBusinessLogic {
    private val worker = WelcomeWorker()
    private var presenter: WelcomePresentationLogic = WelcomePresenter()
    private var disposable: Disposable? = null

    fun setPresenter(presenter: WelcomePresentationLogic) {
        this.presenter = presenter
    }

    override fun generateLicense(request: WelcomeModels.GenerateLicense.Request) {
        // TODO: Call WS to generate license file bin
        val worker = WelcomeWorker()
        worker.generateLicense()

        val response = WelcomeModels.GenerateLicense.Response(true)
        presenter.presentGenerateLicense(response)
    }

    override fun startEnrollment(request: WelcomeModels.StartEnrollment.Request) {
        val response = WelcomeModels.StartEnrollment.Response()
        presenter.presentStartEnrolment(response)
    }

    override fun helloWorld(request: WelcomeModels.HelloWorld.Request) {
        disposable = worker.helloWorld().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { response ->
                    presenter.presentHelloWorld(response)
                    disposable?.dispose()
                },
                { error ->
                    presenter.presentHelloWorld(
                        WelcomeModels.HelloWorld.Response(false, "Web Services Error connection: ${error.localizedMessage}"))
                    disposable?.dispose()
                })
    }
}


/**
 *  Welcome Bussines Logic
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface WelcomeBusinessLogic {
    /**
     * Generate License
     * @param requuest A GenerateLicense Request to send
     */
    fun generateLicense(request: WelcomeModels.GenerateLicense.Request)
    fun startEnrollment(request: WelcomeModels.StartEnrollment.Request)
    fun helloWorld(request: WelcomeModels.HelloWorld.Request)
}
