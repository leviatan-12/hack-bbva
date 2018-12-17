package com.idemia.biosmart.scenes.welcome

/**
 *  Welcome Presenter
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomePresenter : WelcomePresentationLogic {
    private var activity: WelcomeDisplayLogic? = null

    companion object {
        private val TAG = "WelcomePresenter"
    }

    fun setActivity(activity: WelcomeDisplayLogic) {
        this.activity = activity
    }

    override fun presentGenerateLicense(response: WelcomeModels.GenerateLicense.Response) {
        val viewModel = WelcomeModels.GenerateLicense.ViewModel()
        viewModel.generated = response.generated
        activity!!.displayGenerateLicense(viewModel)
    }

    override fun presentStartProcess(response: WelcomeModels.StartEnrollment.Response) {
        val viewModel = WelcomeModels.StartEnrollment.ViewModel(response.operation)
        activity!!.displayStartProcess(viewModel)
    }
}


/**
 *  Welcome Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface WelcomePresentationLogic {
    fun presentGenerateLicense(response: WelcomeModels.GenerateLicense.Response)
    fun presentStartProcess(response: WelcomeModels.StartEnrollment.Response)
}