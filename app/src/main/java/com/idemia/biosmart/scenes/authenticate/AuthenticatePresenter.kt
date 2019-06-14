package com.idemia.biosmart.scenes.authenticate

/**
 *  Authenticate Presenter
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class AuthenticatePresenter : AuthenticatePresentationLogic {
    private var activity: AuthenticateDisplayLogic? = null

    fun setActivity(activity: AuthenticateDisplayLogic) {
        this.activity = activity
    }

    override fun presentGoToNextScene(response: AuthenticateModels.GoToNextScene.Response) {
        val viewModel = AuthenticateModels.GoToNextScene.ViewModel(response.operation)
        activity!!.displayGoToNextScene(viewModel)
    }

    companion object {
        private val TAG = "AuthenticatePresenter"
    }
}


/**
 *  Authenticate Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface AuthenticatePresentationLogic {
    fun presentGoToNextScene(response: AuthenticateModels.GoToNextScene.Response)
}