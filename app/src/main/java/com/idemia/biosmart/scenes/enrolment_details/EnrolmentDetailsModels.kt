package com.idemia.biosmart.scenes.enrolment_details;

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
        class Request(userBiometrics: UserBiometrics)
        class Response(enrolmentResponse: EnrolmentResponse)
        class ViewModel(enrolmentResponse: EnrolmentResponse)
    }
}