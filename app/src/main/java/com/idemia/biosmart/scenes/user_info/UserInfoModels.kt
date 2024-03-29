package com.idemia.biosmart.scenes.user_info;

import com.google.gson.annotations.SerializedName
import com.idemia.morphobiosmart.android.BaseActivity
import com.idemia.biosmart.models.AuthenticationResponse
import com.idemia.biosmart.models.IdentifyResponse

/**
 *  UserInfo Models
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class UserInfoModels {
    class User(val name: String, val last_name:String, val m_last_name:String, val photo: String?, val username: String)
    class SearchPersonDb(val code: Int, val message: String, val user: User?)
    class SearchPersonRequest(val username:String, val os_type: Int, val encrypted: Boolean = false)

    //region Authenticate user
    class AuthenticateUser {
        class Request(val activity: BaseActivity)
        class Response(val authenticationResponse: AuthenticationResponse)
        class ViewModel(val authenticationResponse: AuthenticationResponse)
    }
    //endregion

    //region Identify user
    class IdentifyUser {
        class Request(val activity: BaseActivity)
        class Response(val identifyResponse: IdentifyResponse)
        class ViewModel(val identifyResponse: IdentifyResponse)
    }
    //endregion

    //region Get Person
    class Search {
        data class Request(val activity: BaseActivity, val searchPersonRequest: SearchPersonRequest)

        class Response(val code: Int,
                       @SerializedName("request_type") val requestType: String,
                       val message: String,
                       @SerializedName("code_response") val codeResponse: String,
                       @SerializedName("search_person_db") val searchPersonDb: SearchPersonDb)

        class ViewModel(val userFound: Boolean, val user: User? = null)
    }
    //endregion

    //region Error
    class Error {
        class Response(val throwable: Throwable, val errorCode: Int = -1)
        class ViewModel(val throwable: Throwable, val errorCode: Int = -1)
    }
    //endregion
}