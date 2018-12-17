package com.idemia.biosmart.scenes.user_info;

import com.idemia.biosmart.api.IdemiaApiService
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
        IdemiaApiService.create("http://200.66.76.220:8082/idemia/")
    }

    fun search(request: UserInfoModels.Search.Request): Observable<Response<UserInfoModels.Search.Response>> {
        return apiService.search(request)
    }
}