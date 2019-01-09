package com.idemia.biosmart.scenes.enrolment_details

import android.graphics.BitmapFactory

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

    override fun presentRetrieveUserInfo(response: EnrolmentDetailsModels.RetrieveUserInfo.Response) {
        val viewModel = EnrolmentDetailsModels.RetrieveUserInfo.ViewModel(response.userBiometrics)
        activity!!.displayRetrieveUserInfo(viewModel)
    }

    override fun presentDisplayUserPhoto(response: EnrolmentDetailsModels.DisplayUserPhoto.Response) {
        if(response.photoAvailable){
            val bitmap = BitmapFactory.decodeByteArray(response.image,0 , response.image!!.size)
            val viewModel = EnrolmentDetailsModels.DisplayUserPhoto.ViewModel(true, bitmap)
            activity!!.displayUserPhoto(viewModel)
        }else {
            val viewModel = EnrolmentDetailsModels.DisplayUserPhoto.ViewModel(false)
            activity!!.displayUserPhoto(viewModel)
        }
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
    fun presentRetrieveUserInfo(response: EnrolmentDetailsModels.RetrieveUserInfo.Response)
    fun presentDisplayUserPhoto(response: EnrolmentDetailsModels.DisplayUserPhoto.Response)
    fun presentEnrolPerson(response: EnrolmentDetailsModels.EnrolPerson.Response)
}