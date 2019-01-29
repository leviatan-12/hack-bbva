package com.idemia.biosmart.scenes.enrolment_details;

import com.idemia.biosmart.api.IdemiaApiService
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.models.CreatePersonResponse
import com.idemia.biosmart.models.EnrolmentResponse
import com.idemia.biosmart.models.UserBiometrics
import com.idemia.biosmart.utils.ApiUrlManager
import com.idemia.biosmart.utils.AppCache
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 *  EnrolmentDetails Worker
 *  BioSmart
 *  Created by alfredo on 1/7/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsWorker {

    private fun apiService(activity: BaseActivity): IdemiaApiService {
        val url = ApiUrlManager.url(activity)
        return IdemiaApiService.create(url)
    }

    //region Retrieve user info
    fun retrieveUserInfo(): UserBiometrics {
        val userBiometrics = AppCache.userBiometrics
        return userBiometrics!!
    }
    //endregion

    //region Retrieve user photo
    fun retrieveUserPhoto(): Single<ByteArray> {
        return Single.create<ByteArray> { emitter ->
            AppCache.facePhoto?.let {
                emitter.onSuccess(it.jpegImage)
            } ?: run{
                emitter.onError(Throwable("Face Image Photo not found in app Cache"))
            }
        }
    }
    //endregion

    //region Enrol Person
    fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request): Observable<Response<EnrolmentResponse>> {
        return apiService(request.activity).enrolment(request.userBiometrics)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    //endregion

    //region Create Person
    fun createPerson(request: EnrolmentDetailsModels.CreatePerson.Request): Observable<Response<CreatePersonResponse>> {
        return apiService(request.activity)
            .create(request.userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    //endregion
}