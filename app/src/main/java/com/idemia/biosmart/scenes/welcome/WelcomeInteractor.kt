package com.idemia.biosmart.scenes.welcome

import io.reactivex.disposables.Disposable

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

    override fun startProcess(request: WelcomeModels.StartEnrollment.Request) {
        val response = WelcomeModels.StartEnrollment.Response(request.operation)
        presenter.presentStartProcess(response)
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
    fun startProcess(request: WelcomeModels.StartEnrollment.Request)
}
