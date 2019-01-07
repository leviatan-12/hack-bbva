package com.idemia.biosmart.scenes.enrolment_details

/**
 *  EnrolmentDetails Presenter
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsPresenter : EnrolmentDetailsPresentationLogic {
    companion object {
        private val TAG = "EnrolmentDetailsPresenter"
    }

    private var activity: EnrolmentDetailsDisplayLogic? = null

    fun setActivity(activity: EnrolmentDetailsDisplayLogic) {
        this.activity = activity
    }

    override fun presentEnrolPerson(response: EnrolmentDetailsModels.EnrolPerson.Response) {
        val viewModel = EnrolmentDetailsModels.EnrolPerson.ViewModel(response.enrolmentResponse)
        activity!!.displayEnrolPerson(viewModel)
    }
}


/**
 *  EnrolmentDetails Presentation Logic
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
interface EnrolmentDetailsPresentationLogic {
    fun presentEnrolPerson(response: EnrolmentDetailsModels.EnrolPerson.Response)
}