package com.idemia.biosmart.scenes.enrolment

/**
 *  Enrolment Presenter
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentPresenter : EnrolmentPresentationLogic {
    private var activity: EnrolmentDisplayLogic? = null

    companion object {
        private val TAG = "EnrolmentPresenter"
    }

    fun setActivity(activity: EnrolmentDisplayLogic) {
        this.activity = activity
    }

    override fun presentSaveUserInfo(response: EnrolmentModels.SaveUserInfo.Response) {
        val viewModel = EnrolmentModels.SaveUserInfo.ViewModel()
        activity!!.displaySaveUserInfo(viewModel)
    }

    override fun presentGoToNextScene(response: EnrolmentModels.GoToNextScene.Response) {
        val viewModel = EnrolmentModels.GoToNextScene.ViewModel(response.operation)
        activity!!.displayGoToNextScene(viewModel)
    }
}


/**
 *  Enrolment Presentation Logic
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
interface EnrolmentPresentationLogic {
    fun presentSaveUserInfo(response: EnrolmentModels.SaveUserInfo.Response)
    fun presentGoToNextScene(response: EnrolmentModels.GoToNextScene.Response)
}