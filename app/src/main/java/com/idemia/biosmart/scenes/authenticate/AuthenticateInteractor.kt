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

    //region Go to next scene
    override fun goToNextScene(request: AuthenticateModels.GoToNextScene.Request) {
        val response = AuthenticateModels.GoToNextScene.Response(request.operation)
        presenter.presentGoToNextScene(response)
    }
    //endregion
}


/**
 *  Authenticate Business Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 requestAlfredo. All rights reserved.
 */
interface AuthenticateBusinessLogic {
    /**
     * Go to next Scene
     * @param requuest A DoSomething Request to send
     */
    fun goToNextScene(request: AuthenticateModels.GoToNextScene.Request)
}
