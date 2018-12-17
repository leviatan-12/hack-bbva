package com.idemia.biosmart.scenes.welcome

import com.idemia.biosmart.api.IdemiaApiService
import io.reactivex.Observable

/**
 *  Welcome Worker
 *  BioSmart
 *  Created by alfredo on 12/11/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class WelcomeWorker {

    val apiService by lazy {
        IdemiaApiService.create("http://200.66.76.220:8082/idemia/")
    }

    /**
     * Make your WS calls here
     */
    fun generateLicense() {

    }

    fun helloWorld(): Observable<WelcomeModels.HelloWorld.Response> {
        return apiService.helloWorld()
    }
}