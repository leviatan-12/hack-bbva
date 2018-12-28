package com.idemia.biosmart.scenes.user_info;

import com.idemia.biosmart.api.IdemiaApiService
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
    val apiService by lazy {
        val apiUrl = ApiUrlManager.url()
        IdemiaApiService.create(apiUrl)
    }

    fun search(request: UserInfoModels.Search.Request): Observable<Response<UserInfoModels.Search.Response>> {
        return apiService.search(request)
    }
}