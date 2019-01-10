package com.idemia.biosmart.scenes.fingers_info;

/**
 *  FingersInfo Worker
 *  BioSmart
 *  Created by alfredo on 1/10/19.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class FingersInfoWorker {
    private val key = "IDEMIA_KEY_DO_NOT_SHOW_FINGERS_TUTORIAL"

    fun shouldDisplayTutorial(request: FingersInfoModels.DisplayThisTutorial.Request): Boolean {
        return request.activity.preferenceManager.getBoolean(key, false)
    }

    fun setDisplayThisTutorial(request: FingersInfoModels.SetDisplayThisTutorial.Request){
        request.activity.preferenceManager.edit().putBoolean(key , request.doNotShowTutorial).apply()
    }
}