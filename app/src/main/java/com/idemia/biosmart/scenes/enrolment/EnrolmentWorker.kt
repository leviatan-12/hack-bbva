package com.idemia.biosmart.scenes.enrolment;

import com.idemia.biosmart.utils.AppCache

/**
 *  Enrolment Worker
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentWorker {
    fun saveUserInfo(request: EnrolmentModels.SaveUserInfo.Request){
        AppCache.username = request.username.toString()
        AppCache.name = request.name.toString()
        AppCache.lastName = request.lastName.toString()
        AppCache.secondLastName = request.secondLastName.toString()
    }
}