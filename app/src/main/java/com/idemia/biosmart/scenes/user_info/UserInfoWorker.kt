package com.idemia.biosmart.scenes.user_info;

import com.idemia.biosmart.api.IdemiaApiService
import com.idemia.biosmart.base.android.BaseActivity
import com.idemia.biosmart.utils.ApiUrlManager
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


    fun search(request: UserInfoModels.Search.Request): Observable<Response<UserInfoModels.Search.Response>> {
        return apiService(request.activity).search(request.searchPersonRequest)
    }
}