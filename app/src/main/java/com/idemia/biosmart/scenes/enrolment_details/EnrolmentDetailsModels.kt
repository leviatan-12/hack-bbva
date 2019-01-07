package com.idemia.biosmart.scenes.enrolment_details;

import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.models.EnrolmentResponse
import com.idemia.biosmart.models.UserBiometrics

/**
 *  EnrolmentDetails Models
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsModels {
    // Enrol Person
    class EnrolPerson {
        data class Request(val activity: BaseActivity, val userBiometrics: UserBiometrics)
        data class Response(val enrolmentResponse: EnrolmentResponse)
        data class ViewModel(val enrolmentResponse: EnrolmentResponse)
    }
}