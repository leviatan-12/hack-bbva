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

    override fun presentDoSomething(response: EnrolmentModels.DoSomething.Response) {
        val viewModel = EnrolmentModels.DoSomething.ViewModel()
        activity!!.displayDoSomething(viewModel)
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
    fun presentDoSomething(response: EnrolmentModels.DoSomething.Response)
}