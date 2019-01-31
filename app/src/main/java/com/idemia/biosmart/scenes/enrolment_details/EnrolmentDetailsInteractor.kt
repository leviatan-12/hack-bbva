package com.idemia.biosmart.scenes.enrolment_details

import android.util.Log
import com.idemia.biosmart.base.utils.DisposableManager
import com.idemia.biosmart.models.UserData
import com.idemia.biosmart.utils.AppCache
import com.idemia.biosmart.utils.Base64

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

    //region Retrieve user info
    override fun retrieveUserInfo(request: EnrolmentDetailsModels.RetrieveUserInfo.Request) {
        val userBiometrics = worker.retrieveUserInfo()
        val response = EnrolmentDetailsModels.RetrieveUserInfo.Response(userBiometrics)
        presenter.presentRetrieveUserInfo(response)
    }
    //endregion

    //region Display User Photo
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
    //endregion

    //region Enrol Person
    override fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request) {
        Log.i(TAG, "enrolPerson()")
        DisposableManager.add(worker.enrolPerson(request).subscribe{ responseFromService ->
            if(responseFromService.isSuccessful){
                Log.i(TAG, "enrolPerson: Success")
                val response = EnrolmentDetailsModels.EnrolPerson.Response(responseFromService.body()!!)
                presenter.presentEnrolPerson(response)
            }else {
                Log.e(TAG, "enrolPerson:" + responseFromService.errorBody())
                val response = EnrolmentDetailsModels.Error.Response(Throwable("Communication Error"), responseFromService.code())
                presenter.presentError(response)
            }
        })
    }
    //endregion

    //region Retrieve user data
    override fun retrieveUserData(request: EnrolmentDetailsModels.RetrieveUserData.Request) {
        Log.i(TAG, "retrieveUserData()")
        val name = AppCache.name
        val lastName = AppCache.lastName
        val mLastName = AppCache.secondLastName
        val username = AppCache.username
        var photo: String? = null
        AppCache.facePhoto?.let { morphoImage ->
            photo = Base64.encode(morphoImage.jpegImage)
        }
        val userData = UserData(name!!, lastName!!, mLastName!!, username!!, false, photo)
        val response = EnrolmentDetailsModels.RetrieveUserData.Response(userData)
        presenter.presentRetrieveUserData(response)
    }
    //endregion

    //region Create Person
    override fun createPerson(request: EnrolmentDetailsModels.CreatePerson.Request) {
        Log.i(TAG, "createPerson()")
        DisposableManager.add(worker.createPerson(request).subscribe { responseFromService ->
            if(responseFromService.isSuccessful){
                Log.i(TAG, "createPerson: Success")
                val response = EnrolmentDetailsModels.CreatePerson.Response(responseFromService.body()!!)
                presenter.presentCreatePerson(response)
            }else {
                Log.e(TAG, "createPerson:" + responseFromService.errorBody())
                val response = EnrolmentDetailsModels.Error.Response(Throwable("Communication Error"), responseFromService.code())
                presenter.presentError(response)
            }
        })
    }
    //endregion
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
    fun retrieveUserData(request: EnrolmentDetailsModels.RetrieveUserData.Request)
    fun createPerson(request: EnrolmentDetailsModels.CreatePerson.Request)
}
