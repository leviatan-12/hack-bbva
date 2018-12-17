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

    override fun presentStartEnrolment(response: WelcomeModels.StartEnrollment.Response) {
        val viewModel = WelcomeModels.StartEnrollment.ViewModel()
        activity!!.displayStartEnrolment(viewModel)
    }

    override fun presentHelloWorld(response: WelcomeModels.HelloWorld.Response) {
        val viewModel = WelcomeModels.HelloWorld.ViewModel(true, response.message)
        activity!!.displayHelloWorld(viewModel)
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
    fun presentStartEnrolment(response: WelcomeModels.StartEnrollment.Response)
    fun presentHelloWorld(response: WelcomeModels.HelloWorld.Response)
}