package com.idemia.biosmart.scenes.authenticate

/**
 *  Authenticate Interactor
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class AuthenticateInteractor : AuthenticateBusinessLogic {
    private val worker = AuthenticateWorker()
    private var presenter: AuthenticatePresentationLogic = AuthenticatePresenter()

    fun setPresenter(presenter: AuthenticatePresentationLogic) {
        this.presenter = presenter
    }

    override fun doSomething(request: AuthenticateModels.DoSomething.Request) {
        val response = AuthenticateModels.DoSomething.Response()
        presenter.presentDoSomething(response)
    }
}


/**
 *  Authenticate Business Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface AuthenticateBusinessLogic {
    /**
     * Do Something
     * @param requuest A DoSomething Request to send
     */
    fun doSomething(request: AuthenticateModels.DoSomething.Request)
}
