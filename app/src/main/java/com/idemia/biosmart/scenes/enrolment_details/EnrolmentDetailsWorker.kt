package com.idemia.biosmart.scenes.enrolment_details;

import com.idemia.biosmart.api.IdemiaApiService
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.models.EnrolmentResponse
import com.idemia.biosmart.utils.ApiUrlManager
import io.reactivex.Observable
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

    /**
     * Enrol Person
     */
    fun enrolPerson(request: EnrolmentDetailsModels.EnrolPerson.Request): Observable<Response<EnrolmentResponse>> {
        return apiService(request.activity).enrolment(request.userBiometrics)
    }
}