package com.idemia.biosmart.scenes.enrolment_details

import android.graphics.BitmapFactory
import retrofit2.HttpException

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

    override fun presentError(response: EnrolmentDetailsModels.Error.Response) {
        when(response.errorCode){
            -1 -> {
                val viewModel = EnrolmentDetailsModels.Error.ViewModel(Throwable("[FATAL] Unknown App Error"))
                activity!!.displayError(viewModel)
            }
            404 -> {
                val viewModel = EnrolmentDetailsModels.Error.ViewModel(Throwable("[WS Connection] Middleware URL cannot be reached!"))
                activity!!.displayError(viewModel)
            }else -> {
                val viewModel = EnrolmentDetailsModels.Error.ViewModel(Throwable("[WS Connection] Error code ${response.errorCode}"))
                activity!!.displayError(viewModel)
            }
        }
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
    fun presentError(response: EnrolmentDetailsModels.Error.Response)
}