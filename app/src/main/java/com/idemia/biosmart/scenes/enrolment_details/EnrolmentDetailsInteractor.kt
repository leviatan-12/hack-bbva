package com.idemia.biosmart.scenes.enrolment_details

import android.util.Log
import com.idemia.biosmart.base.utils.DisposableManager
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

/**
 *  EnrolmentDetails Interactor
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsInteractor : EnrolmentDetailsBusinessLogic {
    private val worker = EnrolmentDetailsWorker()
    private var presenter: EnrolmentDetailsPresentationLogic = EnrolmentDetailsPresenter()

    companion object {
        val TAG = "EnrolmentDetailsInt"
    }

    fun setPresenter(presenter: EnrolmentDetailsPresentationLogic) {
        this.presenter = presenter
    }

    override fun retrieveUserInfo(request: EnrolmentDetailsModels.RetrieveUserInfo.Request) {
        val userBiometrics = worker.retrieveUserInfo()
        val response = EnrolmentDetailsModels.RetrieveUserInfo.Response(userBiometrics)
        presenter.presentRetrieveUserInfo(response)
    }

    override fun displayUserPhoto(request: EnrolmentDetailsModels.DisplayUserPhoto.Request) {
        DisposableManager.add(worker.retrieveUserPhoto().subscribe({ image ->
            val response = EnrolmentDetailsModels.DisplayUserPhoto.Response(true, image)
            presenter.presentDisplayUserPhoto(response)
        },{ t ->
            Log.e(TAG, "displayUserPhoto:", t)
            val response = EnrolmentDetailsModels.DisplayUserPhoto.Response(false)
            presenter.presentDisplayUserPhoto(response)
        }))
    }

    override fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request) {
        DisposableManager.add(worker.enrolPerson(request).subscribe{ responseFromService ->
            if(responseFromService.isSuccessful){
                val response = EnrolmentDetailsModels.EnrolPerson.Response(responseFromService.body()!!)
                presenter.presentEnrolPerson(response)
            }else {
                Log.e(TAG, "enrolPerson:" + responseFromService.errorBody())
                val response = EnrolmentDetailsModels.Error.Response(Throwable("Communication Error"), responseFromService.code())
                presenter.presentError(response)
            }
        })
    }
}


/**
 *  EnrolmentDetails Business Logic
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 requestAlfredo. All rights reserved.
 */
interface EnrolmentDetailsBusinessLogic {
    fun retrieveUserInfo(request: EnrolmentDetailsModels.RetrieveUserInfo.Request)
    fun displayUserPhoto(request: EnrolmentDetailsModels.DisplayUserPhoto.Request)
    fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request)
}
