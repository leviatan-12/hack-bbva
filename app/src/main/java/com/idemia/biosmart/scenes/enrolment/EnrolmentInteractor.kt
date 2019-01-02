package com.idemia.biosmart.scenes.enrolment

/**
 *  Enrolment Interactor
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentInteractor : EnrolmentBusinessLogic {
    private val worker = EnrolmentWorker()
    private var presenter: EnrolmentPresentationLogic = EnrolmentPresenter()

    fun setPresenter(presenter: EnrolmentPresentationLogic) {
        this.presenter = presenter
    }

    override fun goToNextScene(request: EnrolmentModels.GoToNextScene.Request) {
        val response = EnrolmentModels.GoToNextScene.Response(request.operation)
        presenter.presentGoToNextScene(response)
    }
}


/**
 *  Enrolment Bussines Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface EnrolmentBusinessLogic {
    /**
     * Do Something
     * @param requuest A DoSomething Request to send
     */
    fun goToNextScene(request: EnrolmentModels.GoToNextScene.Request)
}
