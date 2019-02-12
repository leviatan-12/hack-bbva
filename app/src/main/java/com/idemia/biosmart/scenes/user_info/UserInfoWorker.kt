package com.idemia.biosmart.scenes.user_info;

import com.idemia.biosmart.api.IdemiaApiService
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.models.AuthenticationResponse
import com.idemia.biosmart.models.IdentifyResponse
import com.idemia.biosmart.utils.ApiUrlManager
import com.idemia.biosmart.utils.AppCache
import io.reactivex.Observable
import retrofit2.Response

/**
 *  UserInfo Worker
 *  BioSmart
 *  Created by alfredo on 12/17/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class UserInfoWorker {

    private fun apiService(activity: BaseActivity): IdemiaApiService {
        val url = ApiUrlManager.url(activity)
        return IdemiaApiService.create(url)
    }

    //region Authenticate user
    fun authenticateUser(request: UserInfoModels.AuthenticateUser.Request): Observable<Response<AuthenticationResponse>> {
        return apiService(request.activity).authenticate(AppCache.userBiometrics!!)
    }
    //endregion

    //region Identify user
    fun identifyUser(request: UserInfoModels.IdentifyUser.Request): Observable<Response<IdentifyResponse>>{
        return apiService(request.activity).identify(AppCache.userBiometrics!!)
    }
    //endregion

    //region Search
    fun search(request: UserInfoModels.Search.Request): Observable<Response<UserInfoModels.Search.Response>> {
        return apiService(request.activity).search(request.searchPersonRequest)
    }
    //endregion
}