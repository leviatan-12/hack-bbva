package com.idemia.biosmart.scenes.welcome

/**
 *  Welcome Interactor
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeInteractor : WelcomeBusinessLogic {
    private val worker = WelcomeWorker()
    private var presenter: WelcomePresentationLogic = WelcomePresenter()

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
}
