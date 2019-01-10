package com.idemia.biosmart.scenes.enrolment_details

import android.util.Log
import com.idemia.biosmart.base.utils.DisposableManager
import io.reactivex.disposables.Disposable

/**
 *  EnrolmentDetails Interactor
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsInteractor : EnrolmentDetailsBusinessLogic {
    private val worker = EnrolmentDetailsWorker()
    private var presenter: EnrolmentDetailsPresentationLogic = EnrolmentDetailsPresenter()
    private var dispoable: Disposable? = null

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
        dispoable = worker.retrieveUserPhoto().subscribe({ image ->
            val response = EnrolmentDetailsModels.DisplayUserPhoto.Response(true, image)
            presenter.presentDisplayUserPhoto(response)
        },{ t ->
            Log.e(TAG, "displayUserPhoto:", t)
            val response = EnrolmentDetailsModels.DisplayUserPhoto.Response(false)
            presenter.presentDisplayUserPhoto(response)
        })
        DisposableManager.add(dispoable)
    }

    override fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request) {
        dispoable = worker.enrolPerson(request).subscribe({ responseFromService ->
            val response = EnrolmentDetailsModels.EnrolPerson.Response(responseFromService.body()!!)
            presenter.presentEnrolPerson(response)
        },{ throwable ->
            Log.e(TAG, "enrolPerson:" ,throwable)
        })
        DisposableManager.add(dispoable)
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
