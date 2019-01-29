package com.idemia.biosmart.scenes.enrolment_details;

import android.graphics.Bitmap
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.models.CreatePersonResponse
import com.idemia.biosmart.models.EnrolmentResponse
import com.idemia.biosmart.models.UserBiometrics
import com.idemia.biosmart.models.UserData

/**
 *  EnrolmentDetails Models
 *  BioSmart
 *  Created by alfredo on 1/2/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class EnrolmentDetailsModels {

    // Retrieve User Info
    class RetrieveUserInfo{
        class Request
        data class Response(val userBiometrics: UserBiometrics)
        data class ViewModel(val userBiometrics: UserBiometrics)
    }

    // Display User Info
    class DisplayUserPhoto {
        class Request
        data class Response(val photoAvailable: Boolean, val image: ByteArray? = null)
        data class ViewModel(val photoAvailable: Boolean, val bitmap: Bitmap? = null)
    }

    // Enrol Person
    class EnrolPerson {
        data class Request(val activity: BaseActivity, val userBiometrics: UserBiometrics)
        data class Response(val enrolmentResponse: EnrolmentResponse)
        data class ViewModel(val enrolmentResponse: EnrolmentResponse)
    }

    // Retrieve User Data
    class RetrieveUserData{
        class Request
        data class Response(val userData: UserData)
        data class ViewModel(val userData: UserData)
    }

    // Create Person
    class CreatePerson {
        data class Request(val activity: BaseActivity, val userData: UserData)
        class Response(val createPersonResponse: CreatePersonResponse)
        class ViewModel(val createPersonResponse: CreatePersonResponse)
    }

    // Error
    class Error {
        class Response(val throwable: Throwable, val errorCode: Int = -1)
        class ViewModel(val throwable: Throwable, val errorCode: Int = -1)
    }
}