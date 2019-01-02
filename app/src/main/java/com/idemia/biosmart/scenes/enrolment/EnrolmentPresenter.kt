package com.idemia.biosmart.scenes.enrolment

/**
 *  Enrolment Presenter
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentPresenter : EnrolmentPresentationLogic {
    private var activity: EnrolmentDisplayLogic? = null

    fun setActivity(activity: EnrolmentDisplayLogic) {
        this.activity = activity
    }

    override fun presentGoToNextScene(response: EnrolmentModels.GoToNextScene.Response) {
        val viewModel = EnrolmentModels.GoToNextScene.ViewModel(response.operation)
        activity!!.displayGoToNextScene(viewModel)
    }

    companion object {
        private val TAG = "EnrolmentPresenter"
    }
}


/**
 *  Enrolment Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface EnrolmentPresentationLogic {
    fun presentGoToNextScene(response: EnrolmentModels.GoToNextScene.Response)
}