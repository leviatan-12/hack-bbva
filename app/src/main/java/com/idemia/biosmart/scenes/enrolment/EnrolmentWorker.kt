package com.idemia.biosmart.scenes.enrolment;

import com.idemia.biosmart.utils.AppCache

/**
 *  Enrolment Worker
 *  BioSmart
 *  Created by alfredo on 12/14/18.
 *  Copyright (c) 2018 Alfredo. All rights reserved.
 */
class EnrolmentWorker {
    //region Save user info
    fun saveUserInfo(request: EnrolmentModels.SaveUserInfo.Request){
        AppCache.username = request.username
        AppCache.name = request.name
        AppCache.lastName = request.lastName
        AppCache.secondLastName = request.secondLastName
    }
    //endregion
}