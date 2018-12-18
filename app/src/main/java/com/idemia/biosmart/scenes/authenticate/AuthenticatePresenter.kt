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

    override fun presentDoSomething(response: AuthenticateModels.DoSomething.Response) {
        val viewModel = AuthenticateModels.DoSomething.ViewModel()
        activity!!.displayDoSomething(viewModel)
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
    fun presentDoSomething(response: AuthenticateModels.DoSomething.Response)
}