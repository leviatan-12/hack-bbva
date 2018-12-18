package com.idemia.biosmart.scenes.identify

/**
 *  Identify Interactor
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class IdentifyInteractor : IdentifyBusinessLogic {
    private val worker = IdentifyWorker()
    private var presenter: IdentifyPresentationLogic = IdentifyPresenter()

    fun setPresenter(presenter: IdentifyPresentationLogic) {
        this.presenter = presenter
    }

    override fun doSomething(request: IdentifyModels.DoSomething.Request) {
        val response = IdentifyModels.DoSomething.Response()
        presenter.presentDoSomething(response)
    }
}


/**
 *  Identify Business Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface IdentifyBusinessLogic {
    /**
     * Do Something
     * @param requuest A DoSomething Request to send
     */
    fun doSomething(request: IdentifyModels.DoSomething.Request)
}
